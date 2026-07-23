import os

from flask import Blueprint, jsonify, request
from sqlalchemy.exc import SQLAlchemyError

from .db import db
from .model_service import DEPARTMENT_QUERY, classify_text, process_complaint
from .models import Complaint, ComplaintWork, Department, Guest

complaints = Blueprint("complaints", __name__, url_prefix="/api/complaints")


def _department_for(label):
    names = DEPARTMENT_QUERY[label]
    return Department.query.filter(Department.dept_name.in_(names)).order_by(Department.dept_id).first()


def _filter_rejection(label, field):
    subject = "민원 제목" if field == "title" else "민원 내용"
    message = (
        f"{subject}에 욕설/비방이 포함되어 정상접수 되지 못했습니다. 다시 신청해주세요."
        if label == "WARNING" else
        f"{subject}이 의미 없는 문장으로 판단되어 정상접수 되지 못했습니다. 다시 신청해주세요."
    )
    return jsonify(
        accepted=False,
        filterLabel=label,
        rejectedField=field,
        standardizedComplaint=None,
        categories=[],
        refinedContent=None,
        classifications=[],
        departments=[],
        message=message,
    ), 422


@complaints.post("")
def submit_complaint():
    data = request.get_json(silent=True) or {}
    title = str(data.get("title", "")).strip()
    content = str(data.get("content", "")).strip()
    if not title or not content:
        return jsonify(error="VALIDATION_ERROR", message="제목과 민원 내용은 필수입니다."), 400

    try:
        # 제목도 기준 노트북과 동일한 욕설/비방/무의미 문장 분류기를 거친다.
        # 제목이 거절되면 표준화·부서분류 및 아래 DB 트랜잭션에는 진입하지 않는다.
        title_label = classify_text(title)
        
        if title_label != "NORMAL":
            return _filter_rejection(title_label, "title")
        analysis = process_complaint(content)
    except Exception as error:
        return jsonify(error="MODEL_UNAVAILABLE", message="AI 민원 처리 모델을 사용할 수 없습니다.", detail=str(error)), 503

    if not analysis["accepted"]:
        return _filter_rejection(analysis["label"], "content")

    try:
        user_id = data.get("userId")
        guest_id = None
        if not user_id:
            name = str(data.get("name", "")).strip()
            phone = str(data.get("phone", "")).strip()
            if not name or not phone:
                return jsonify(error="VALIDATION_ERROR", message="비회원은 이름과 전화번호가 필수입니다."), 400
            guest = Guest.query.filter_by(phone=phone).first()
            if guest is None:
                guest = Guest(name=name, phone=phone)
                db.session.add(guest)
                db.session.flush()
            guest_id = guest.guest_id

        complaint = Complaint(
            account_id=user_id,
            guest_id=guest_id,
            title=title,
            password=data.get("password") or None,
            content=content,
            refined_content=analysis["standardized_complaint"],
            is_public="Y" if data.get("isPublic") in {True, "Y", "public"} else "N",
        )
        db.session.add(complaint)
        db.session.flush()

        department_names = []
        for sequence, label in enumerate(analysis["categories"], start=1):
            department = _department_for(label)
            if department is None:
                raise RuntimeError(f"'{label}'에 대응하는 기존 DEPARTMENT 행이 없습니다.")
            db.session.add(ComplaintWork(
                complaint_id=complaint.complaint_id,
                status_id=int(os.getenv("INITIAL_STATUS_ID", "1")),
                dept_id=department.dept_id,
                work_sequence=sequence,
            ))
            department_names.append(department.dept_name)

        db.session.commit()
        status = 201
        message = "정상접수 되었습니다. 감사합니다."
        return jsonify(
            accepted=analysis["accepted"], complaintId=complaint.complaint_id,
            filterLabel=analysis["label"],
            standardizedComplaint=analysis["standardized_complaint"],
            categories=analysis["categories"],
            # 기존 프론트엔드 응답 필드도 유지한다.
            refinedContent=analysis["standardized_complaint"],
            classifications=analysis["categories"],
            departments=department_names,
            message=message,
        ), status
    except (SQLAlchemyError, RuntimeError) as error:
        db.session.rollback()
        return jsonify(error="PERSISTENCE_ERROR", message="민원 저장 중 오류가 발생했습니다.", detail=str(error)), 500


@complaints.get("/officer")
def officer_complaints():
    rows = (
        db.session.query(Complaint, ComplaintWork, Department)
        .join(ComplaintWork, ComplaintWork.complaint_id == Complaint.complaint_id)
        .join(Department, Department.dept_id == ComplaintWork.dept_id)
        .filter(Complaint.refined_content.is_not(None))
        .order_by(Complaint.complaint_id.desc())
        .all()
    )
    return jsonify([{
        "complaintId": c.complaint_id, "title": c.title, "content": c.content,
        "refinedContent": c.refined_content, "createdAt": c.created_at.isoformat() if c.created_at else None,
        "workId": s.work_id, "separatedId": s.work_id,
        "currentDeptId": d.dept_id, "department": d.dept_name,
    } for c, s, d in rows])
