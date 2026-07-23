from sqlalchemy import FetchedValue

from .db import db


class UserInfo(db.Model):
    __tablename__ = "USER_INFO"
    account_id = db.Column("ACCOUNT_ID", db.Integer, primary_key=True)


class Status(db.Model):
    __tablename__ = "STATUS"
    status_id = db.Column("STATUS_ID", db.Integer, primary_key=True)
    status_name = db.Column("STATUS_NAME", db.String(50), nullable=False)


class Guest(db.Model):
    __tablename__ = "GUEST"
    guest_id = db.Column("GUEST_ID", db.Integer, primary_key=True)
    name = db.Column("NAME", db.String(50), nullable=False)
    phone = db.Column("PHONE", db.String(20), nullable=False)
    created_at = db.Column("CREATED_AT", db.DateTime, nullable=False, server_default=FetchedValue())


class Complaint(db.Model):
    __tablename__ = "COMPLAINT"
    complaint_id = db.Column("COMPLAINT_ID", db.Integer, primary_key=True)
    account_id = db.Column("ACCOUNT_ID", db.Integer, db.ForeignKey("USER_INFO.ACCOUNT_ID"))
    guest_id = db.Column("GUEST_ID", db.Integer, db.ForeignKey("GUEST.GUEST_ID"))
    title = db.Column("TITLE", db.String(200), nullable=False)
    password = db.Column("PASSWORD", db.String(30))
    content = db.Column("CONTENT", db.Text, nullable=False)
    # 기존 DB 컬럼을 매핑할 뿐 create_all/DDL은 실행하지 않는다.
    refined_content = db.Column("REFINED_CONTENT", db.Text)
    is_public = db.Column("IS_PUBLIC", db.String(1), nullable=False)
    created_at = db.Column("CREATED_AT", db.DateTime, nullable=False, server_default=FetchedValue())


class Department(db.Model):
    __tablename__ = "DEPARTMENT"
    dept_id = db.Column("DEPT_ID", db.Integer, primary_key=True)
    dept_name = db.Column("DEPT_NAME", db.String(100), nullable=False)


class ComplaintWork(db.Model):
    __tablename__ = "COMPLAINT_WORK"
    work_id = db.Column("WORK_ID", db.Integer, primary_key=True)
    complaint_id = db.Column("COMPLAINT_ID", db.Integer, db.ForeignKey("COMPLAINT.COMPLAINT_ID"), nullable=False)
    parent_work_id = db.Column("PARENT_WORK_ID", db.Integer, db.ForeignKey("COMPLAINT_WORK.WORK_ID"))
    dept_id = db.Column("DEPT_ID", db.Integer, db.ForeignKey("DEPARTMENT.DEPT_ID"), nullable=False)
    account_id = db.Column("ACCOUNT_ID", db.Integer)
    status_id = db.Column("STATUS_ID", db.Integer, db.ForeignKey("STATUS.STATUS_ID"), nullable=False)
    work_result_code_id = db.Column("WORK_RESULT_CODE_ID", db.Integer)
    work_sequence = db.Column("WORK_SEQUENCE", db.Integer, nullable=False)
    started_at = db.Column("STARTED_AT", db.DateTime, nullable=False, server_default=FetchedValue())
    due_at = db.Column("DUE_AT", db.DateTime)
    ended_at = db.Column("ENDED_AT", db.DateTime)
    created_by_account_id = db.Column("CREATED_BY_ACCOUNT_ID", db.Integer)
    enabled = db.Column("ENABLED", db.String(1), nullable=False, server_default=FetchedValue())
    created_at = db.Column("CREATED_AT", db.DateTime, nullable=False, server_default=FetchedValue())
    updated_at = db.Column("UPDATED_AT", db.DateTime, nullable=False, server_default=FetchedValue())
