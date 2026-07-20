import '../../styles/pages/PolicyPage.css';
import { useState } from 'react';
import {useNavigate} from "react-router-dom";
import { setInquiryInsert } from '../../springApi/inquirySpringBootApi';
import { useAuth } from '../user/AuthContext';

function CustomerCenterPage() {
  const { user } = useAuth();

  const [formData, setFormData] = useState({
    inq_title: '',
    inq_content: '',
    mem_id: user?.mem_id
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    try{
        alert('문의가 접수되었습니다.');
        setInquiryInsert(formData);
        navigate("/");
    }

    catch(error){
       console.log(error);
       alert("문의 접수 실패");
    }   
};

return (
    <main className="page policy-page">
      <section className="card policy-card">
        <h1>고객센터</h1>
        <p className="policy-desc">서비스 이용 중 궁금한 점이나 불편한 점을 남겨주세요.</p>

        <div className="customer-grid">
          <div className="customer-info-box">
            <h2>문의 안내</h2>
            <p>운영 시간 : 평일 09:00 ~ 18:00</p>
            <p>점심 시간 : 12:00 ~ 13:00</p>
            <p>답변은 순차적으로 처리됩니다.</p>
          </div>

          <form className="customer-form" onSubmit={handleSubmit}>
            <label>문의 제목</label>
            <input name="inq_title" value={formData.inq_title} onChange={handleChange} required />

            <label>답변 받을 이메일</label>
            <input name="mem_id" type="email" value={formData.mem_id} onChange={handleChange} readOnly required />

            <label>문의 내용</label>
            <textarea name="inq_content" value={formData.inq_content} onChange={handleChange} required />

            <button className="btn-primary small" type="submit">문의 접수</button>
          </form>
        </div>
      </section>
    </main>
  );
}

export default CustomerCenterPage;
