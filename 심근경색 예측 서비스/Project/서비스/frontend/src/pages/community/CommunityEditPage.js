import React, { useState, useEffect } from 'react';
import {useNavigate,useParams} from 'react-router-dom';
import '../../styles/pages/CommunityPage.css';
import {getCommunityView, setCommunityUpdate} from "../../springApi/communitySpringBootApi";

function CommunityEditPage() {

    const navigate = useNavigate();
    const { postId } = useParams();

    /**
     * 게시글 state
     */
    const [post, setPost] = useState({

      mem_id : "",
      com_category : "",
      com_title : "",
      com_content : ""
  });

    /**
     * 수정할 게시글 조회
     */
    useEffect(() => {

    const loadData = async() => {
        try{

        const response = await getCommunityView(postId);
        setPost(response.data);
        }

        catch(error){

        console.log(error);
        alert(
            "게시글 조회 실패하였습니다!!!"
        );
        }
    };

    loadData();

    }, [postId]);

    /**
     * 입력값 변경
     */
    const changePost = (e) => {

    setPost({

        ...post,

        [e.target.name] : e.target.value
    });
    };

    /**
     * 게시글 수정
     */
    const handleSubmit = async(e) => {

    e.preventDefault();

    try{
        await setCommunityUpdate(post);

        alert(
        "게시글 수정 완료하였습니다!!!"
        );

        navigate(`/community/${postId}`);

    }

    catch(error){

        console.log(error);

        alert(
        "게시글 수정 실패하였습니다!!!"
        );
    }
  };

  return (

    <main className="page community-write-page">

      <section className="card write-card">

        <h2>
          게시글 수정
        </h2>

        <form onSubmit={handleSubmit}>

          {/* 카테고리 */}

          <div className="form-group">

            <label>
              카테고리
            </label>

            <select
              name="com_category"
              value={post.com_category}
              onChange={changePost}>

              <option value="자유 게시판">
                자유 게시판
              </option>

              <option value="운동 공유">
                운동 공유
              </option>

              <option value="식단이야기">
                식단 이야기
              </option>

              <option value="건강정보">
                건강정보
              </option>

            </select>

          </div>

          {/* 제목 */}

          <div className="form-group">

            <label>
              제목
            </label>

            <input
              type="text"
              name="com_title"
              value={post.com_title}
              onChange={changePost}
              required
            />

          </div>

          {/* 내용 */}

          <div className="form-group">

            <label>
              내용
            </label>

            <textarea
              rows="10"
              name="com_content"
              value={post.com_content}
              onChange={changePost}
              required
            />

          </div>

          {/* 버튼 */}

          <div className="write-button-group">

            <button type="submit"
                    className="btn-primary">

              수정

            </button>

            <button type="button"
                    className="btn-outline"
                    onClick={() => navigate(-1)}>

              취소

            </button>

          </div>

        </form>

      </section>

    </main>
  );
}

export default CommunityEditPage;