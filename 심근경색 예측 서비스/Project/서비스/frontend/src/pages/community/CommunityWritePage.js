import '../../styles/pages/CommunityPage.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { setCommunityInsert} from "../../springApi/communitySpringBootApi";

function CommunityWritePage() {
  const navigate = useNavigate();
  const [post, setPost] = useState({ com_category: '건강 정보', mem_id : "", com_title: '', com_content: '' });

  const handleChange = (e) => {
    setPost({
            ...post,
            [e.target.name] : e.target.value
      });
  };

  const handleSubmit = async(e) => {
  e.preventDefault();

  const user_info = JSON.parse(
      localStorage.getItem("user_info")
    );

  try{
        const insertPost = {...post, mem_id : user_info?.mem_id
    };

    console.log(insertPost);
    await setCommunityInsert(insertPost);
    alert(
      '게시글이 등록되었습니다.'
    );

    window.location.href = "/community";
    navigate('/community');
  }

  catch(error){
    console.log(error);
    alert('등록 실패했습니다!!!');
  }
};

  return (
    <main className="page community-write-page">
      <section className="card write-card">
        <h2>게시글 작성</h2>
        <form onSubmit={handleSubmit} className="write-form">
          <label>카테고리</label>
          <select name="com_category" value={post.com_category} onChange={handleChange}>
            <option>건강 정보</option>
            <option>식단 이야기</option>
            <option>운동 공유</option>
            <option>자유 게시판</option>
          </select>

          <label>제목</label>
          <input name="com_title" value={post.com_title} onChange={handleChange} placeholder="제목을 입력하세요" required />

          <label>내용</label>
          <textarea name="com_content" value={post.com_content} onChange={handleChange} placeholder="내용을 입력하세요" required />

          <div className="write-buttons">
            <button type="button" className="btn-outline" onClick={() => navigate('/community')}>취소</button>
            <button type="submit" className="btn-primary">등록하기</button>
          </div>
        </form>
      </section>
    </main>
  );
}

export default CommunityWritePage;
