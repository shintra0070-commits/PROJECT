import '../../styles/pages/MyPage.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../user/AuthContext';
import axios from 'axios';
import { checkTodayPredict  } from "../../springApi/modeldataSpringBootApi";

// MyPage.js 상단 import에 추가
import { getDataView } from '../../springApi/modeldataSpringBootApi';
import { getMemberView, setMemberDelete, setMemberUpdate } from '../../springApi/memberSpringBootApi';
import {getInquiryList} from "../../springApi/inquirySpringBootApi"; 
import {setAnalysisDelete} from "../../springApi/modeldataSpringBootApi"; 

function MyPage() {
    const navigate = useNavigate();
    const { user, login, logout } = useAuth() || {}; // [수정] 수정 완료 후 전역 세션 갱신을 위해 login 함수 구독

    // 마이페이지 왼쪽 버튼 클릭 상태 관리
    const [activeMenu, setActiveMenu] = useState('내 정보');

    const [boardList, setBoardList] = useState([]);
    const [boardLoading, setBoardLoading] = useState(true);

    // [추가] 회원 정보 수정 폼을 위한 독립 상태 변수 선언
    const [editForm, setEditForm] = useState({
        mem_id: "",
        mem_name: "",
        mem_phone: "",
        mem_nickname: ""
    });

    // 정적 가상 데이터 (분석 기록용)
    const [records, setRecords] = useState([]);
    const [recordLoading, setRecordLoading] = useState(true);

    useEffect(() => {

        const memId = user?.mem_id || user?.id;  // ✅ ID로 수정
        getDataView(memId)
            .then((response) => {
                if (!response?.data || response.data.length === 0) {
                    setRecords([]);
                    return;
                }

                const sorted = [...response.data]
                    .sort((a, b) => new Date(b.CHECK_DATE) - new Date(a.CHECK_DATE))
                setRecords(sorted);
            })

            .catch(() => setRecords([]))
            .finally(() => setRecordLoading(false));
            
    if (!user) return;

    const mem_id = user?.mem_id || user?.id;

    // 로컬스토리지 기본값 먼저 세팅 (빠른 렌더링)
    setEditForm({
        mem_id: mem_id || "",
        mem_name: user?.mem_name || user?.name || "",
        mem_phone: "",
        mem_nickname: ""
    });

    // DB에서 전화번호 + 닉네임 조회
    getMemberView(mem_id)
        .then((res) => {
            const data = res.data;
            setEditForm((prev) => ({
                ...prev,
                mem_phone: data.mem_phone || "",
                mem_nickname: data.mem_nickname || ""
            }));
        })
        .catch((err) => {
            console.error("❌ 회원 상세 조회 실패:", err);
        });

}, [user]);


useEffect(() => {

    console.log("유저 정보:", user);

    axios.get(`http://localhost:8080/community/board/my/${user?.mem_id}`).then((res) => {

            console.log("게시글 데이터:", res.data);

            setBoardList(res.data);

        })

        .catch((err) => {

            console.log("에러:", err);

        })

        .finally(() => {

            setBoardLoading(false);

        });

}, [user]);

    const handleRecordDetail = (record) => {
        navigate('/result/detail', { state: { record } });
    };

    // [추가] 입력 박스 값 변경 핸들러
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditForm((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    // [추가] 백엔드 API 송신 및 전역 상태 동기화 처리 핸들러
    const handleUpdateSubmit = (e) => {
        e.preventDefault();

        // 비즈니스 유효성 검증
        if (!editForm.mem_name.trim()) {
            alert("이름은 필수 입력 항목입니다.");
            return;
        }

        console.log("📤 [회원정보수정] 백엔드 전송 데이터:", editForm);

        setMemberUpdate(editForm)
            .then((res) => {
                // [핵심 교정] 화면 전체의 네비게이션 바 및 프로필 뷰 동기화를 위한 객체 재정비
                const updatedSessionUser = {
                    // id: editForm.mem_id,
                    // name: editForm.mem_name,
                    // phone: editForm.mem_phone,
                    // nickname: editForm.mem_nickname,
                    
                    // 호환성을 위한 원본 데이터 맵 유지
                    mem_id: editForm.mem_id,
                    mem_name: editForm.mem_name,
                    mem_phone: editForm.mem_phone,
                    mem_nickname: editForm.mem_nickname
                };

                // 로컬 스토리지 데이터 및 AuthContext 내부 State 동시 갱신 
                login(updatedSessionUser);

                alert("회원 정보 수정이 정상적으로 완료되었습니다.");
                setActiveMenu('내 정보'); // 수정 완료 후 대시보드로 복귀
            })

            .catch((err) => {
                console.error("❌ [회원정보수정] API 통신 실패:", err);
                if (err.response) {
                    alert(`서버 오류 (코드: ${err.response.status}): 수정에 실패했습니다.`);
                } else {
                    alert("네트워크 물리 오류가 발생했습니다. 서버 상태를 확인하세요.");
                }
            });
    };

    /**
    * 내 문의사항
    */
    const [myInquiryList, setMyInquiryList] = useState([]);

    /**
     * 로딩 상태
    */
    const [inquiryLoading, setInquiryLoading] = useState(false);

    /**
     * 내 게시글 페이지
    */
    const [boardPage, setBoardPage] = useState(1);

    /**
     * 내 문의사항 페이지
    */
    const [inquiryPage, setInquiryPage] = useState(1);

    /**
     * 페이지당 개수
    */
    const itemsPerPage = 5;

    useEffect(() => {

    /**
     * 내 문의사항 조회
    */
    const loadMyInquiry =
        async() => {

        try{
            setInquiryLoading(true);
            const response = await getInquiryList();

            // 내 문의만 필터링
            const myList = response.data.filter(

                    (item) => item.mem_id === user?.mem_id);

            setMyInquiryList(
                myList
            );
        }

        catch(error){

            console.log(error);
            alert("문의사항 조회 실패");
        }

        finally{

            setInquiryLoading(false);
        }
    };

    // 내 문의사항 조회
    if(activeMenu ==='내 문의사항 조회'){

        loadMyInquiry();
    }

    }, [activeMenu, user?.mem_id]);
    

    const handleAnalysis = async () => {
    try {
        const checkres = await checkTodayPredict(user?.mem_id);
        
        if (checkres.data === true) {
            alert("오늘은 이미 예측을 완료했습니다.\n예측은 하루에 한 번만 가능합니다.");
            return; // alert 후 현재 페이지 그대로 유지
        }
        
        navigate("/analysis");
        
    } 
    
    catch (error) {
        console.error("예측 여부 확인 실패:", error);
        alert("예측 여부 확인 중 오류가 발생했습니다.");
    }
};
    /**
     * 내 게시글 paging
    */
    const boardLastIndex = boardPage * itemsPerPage;
    const boardFirstIndex =  boardLastIndex - itemsPerPage;
    const currentBoardList = boardList.slice(boardFirstIndex, boardLastIndex);
    const boardTotalPages = Math.ceil( boardList.length /itemsPerPage);

    /**
     * 내 문의사항 최신순 정렬
    */
    const sortedInquiryList = [...myInquiryList].sort(

        (a, b) => {

            // 날짜 최신순
            const dateCompare = new Date(b.inq_created) - new Date(a.inq_created);

            if(dateCompare !== 0){

                return dateCompare;
            }

            // 같은 날짜면 ID 최신순
            return b.inq_id - a.inq_id;
        }
    );

    /**
     * 내 문의사항 paging
    */
    const inquiryLastIndex = inquiryPage * itemsPerPage;
    const inquiryFirstIndex = inquiryLastIndex - itemsPerPage;
    const currentInquiryList = sortedInquiryList.slice(inquiryFirstIndex,inquiryLastIndex);
    const inquiryTotalPages = Math.ceil(sortedInquiryList.length /itemsPerPage);


    const handleDelete = async () => {
    if (!window.confirm("정말로 탈퇴하시겠습니까?")) {
        return;
    }

    try {
        await setMemberDelete(user?.mem_id);
        alert("회원 탈퇴가 완료되었습니다.");
        logout();
        navigate("/");
    }

    catch (error) {
        console.log(error);
        alert("회원 탈퇴에 실패했습니다.");
        }
    };

    const handleDeleteAnalysis = async (dataId) => {
    if (!window.confirm("이 분석 기록을 삭제하시겠습니까?")) return;

    try {
        await setAnalysisDelete(dataId);
        // 성공 시 프론트 상태에서도 제거
        setRecords((prev) => prev.filter((r) => r.DATA_ID !== dataId));
        alert("삭제되었습니다.");
    } catch (error) {
        console.error("삭제 실패:", error);
        alert("삭제에 실패했습니다.");
    }
};

    
    return (
        <main className="page mypage">
        <section className="mypage-layout">
            <aside className="card mypage-sidebar">
            <button
                className={activeMenu === '내 정보' ? 'active' : ''}
                onClick={() => setActiveMenu('내 정보')}>
                내 정보
            </button>

            <button 
                className = {activeMenu === '내 게시글 조회' ? 'active' : ''}
                onClick={() => {setActiveMenu('내 게시글 조회');}}>
                내 게시글 조회
            </button>
            
            <button 
                className={activeMenu === '내 문의사항 조회' ? 'active' : ''}
                onClick={() => setActiveMenu('내 문의사항 조회')}>
                내 문의사항 조회
            </button>

            <button 
                className={activeMenu === '회원 정보' ? 'active' : ''}
                onClick={() => setActiveMenu('회원 정보')}>
                회원 정보
            </button>

            <button
                className={activeMenu === '분석 기록' ? 'active' : ''}
                onClick={() => setActiveMenu('분석 기록')}>
                분석 기록
            </button>

            </aside>

            <section className="mypage-content">
            {activeMenu === '내 정보' && (
                <>
                <div className="card profile-card">
                    <h3>내 정보</h3>

                    <div className="profile-box">
                    <div className="profile-img">👨‍💻</div>

                    <div>
                        <h2>{user?.mem_name || user?.name || '홍길동'}</h2>
                        <p>{user?.mem_id || user?.id || 'hong@example.com'}</p>

                        <button className="btn-outline mint"
                                onClick={() => setActiveMenu('회원 정보')}>

                            정보 수정
                        </button>
                        <button className="btn-outline mint"
                                onClick={handleDelete}
                                style={{marginLeft:"10px"}}>

                            회원 탈퇴
                        </button>
                    </div>
                    </div>
                </div>
                    

                <div className="card record-card">
                    <h3 style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                        최근 분석 기록{records.length === 0 ? (<button className="btn-outline mint" onClick={handleAnalysis}>예측하러가기</button>
                        ) : (
                            <button className="btn-outline mint" onClick={handleAnalysis}>예측하기</button>
                        )}
                    </h3>
                    
                    {recordLoading ? (<p style={{ color: '#888', fontSize: '14px' }}>불러오는 중...</p>) : records.length === 0 ? (
                        <p style={{ color: '#888', fontSize: '14px' }}>분석 기록이 없습니다.</p>                        
                    ) : (
                        records.slice(0, 3).map((record, index) => (
                            <div className="record-row" key={index}>
                                <span>{record.CHECK_DATE}</span>
                                <strong>{(record.PREDICT * 100).toFixed(1)}% </strong>
                                <button className="btn-outline mint" onClick={() => handleRecordDetail(record)}>상세보기</button>
                            </div>
                        ))
                    )}
                </div>  
            </>
        )}

            {activeMenu === '내 게시글 조회' && (
            <div className="card mypage-full-card">
                <div className="title-row">
                    <h3>
                        내 게시글
                    </h3>

                    <button className="btn-outline mint"
                            onClick={() => navigate('/community/write')}>

                        게시글 작성하기
                    </button>

                </div>
                        <p>
                            지금까지 내가 작성한 게시글입니다
                        </p>

                        {boardLoading ? (
                            <p style={{ color: '#888', fontSize: '14px' }}>
                                불러오는 중...
                            </p>
                        ) : boardList.length === 0 ? (
                            <p style={{ color: '#888', fontSize: '14px' }}>
                                작성한 게시글이 없습니다.
                            </p>
                        ) : 
                        
            <>

            <div className="board-header">
                <span>제목</span>
                <span>시간</span>
            </div>

            {currentBoardList.map((board, index) => (

                <div className="record-row large" key={index}>

                    <strong className="board-title">
                        {board.com_title}
                    </strong>

                    <span className="board-date">
                        {board.com_created?.slice(0, 16).replace('T', ' ')}
                    </span>

                    <button className="btn-outline mint"
                            onClick={() =>  navigate(`/community/${board.com_id}`)}>
                        상세보기
                    </button>

                </div>

                    ))}
                </>             
            }
            
            <div className="pagination">
                <button onClick={() =>setBoardPage(boardPage <= 1 ? boardTotalPages: boardPage - 1)}>
                    〈
                </button>

            {Array.from({length:boardTotalPages},

                (_, i) => (
                <button key={i + 1}
                        className={ boardPage === i + 1? "active": ""}
                        onClick={() =>  setBoardPage(i + 1)}>
                        {i + 1}
                </button>
            ))}

            <button onClick={() => setBoardPage(boardPage >= boardTotalPages ? 1: boardPage + 1)}>
                〉
            </button>

        </div>

        </div>
    )}
            {activeMenu === '분석 기록' && (
                <div className="card mypage-full-card">
                    <h3 style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                        분석 기록
                        <button onClick={handleAnalysis} className="btn-outline mint">예측하기</button>
                    </h3>
                    <p>지금까지 진행한 심근경색 발생 확률 예측 기록입니다.</p>

                    {recordLoading ? (
                        <p style={{ color: '#888', fontSize: '14px' }}>불러오는 중...</p>
                    ) : records.length === 0 ? (
                        <p style={{ color: '#888', fontSize: '14px' }}>분석 기록이 없습니다.</p>
                    ) : (
                        records.map((record, index) => (
                            <div className="record-row large" key={index}
                                style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
                                <span>{record.CHECK_DATE}</span>
                                <strong>{(record.PREDICT * 100).toFixed(1)}%</strong>
                                <div style={{ display: "flex", gap: "8px" }}>
                                    <button className="btn-outline mint"
                                        onClick={() => handleRecordDetail(record)}>
                                        상세보기
                                    </button>
                                    <button className="btn-outline"
                                        onClick={() => handleDeleteAnalysis(record.DATA_ID)}
                                        style={{ color: "#ef4444", borderColor: "#ef4444" }}>
                                        삭제
                                    </button>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            )}

            {activeMenu === '내 문의사항 조회' && (
            <div className="card mypage-full-card">
                <div className="title-row">
                    <h3>
                        내 문의사항
                    </h3>

                <button className="btn-outline mint" onClick={() =>navigate('/customer-center')}>

                    문의사항 작성하기
                </button>
            </div>
            <p>
                내가 작성한 문의사항 목록입니다.
            </p>

            {inquiryLoading ? (

                <p>
                    불러오는 중...
                </p>

            ) : currentInquiryList.length > 0 ? (

                <>
                    <table className="post-table">
                        <thead>
                            <tr>
                                <th>제목</th>
                                <th>문의내용</th>
                                <th>작성일</th>
                            </tr>

                        </thead>

                    <tbody>

                        {currentInquiryList.map(

                            (item) => (

                            <tr key={item.inq_id}>

                                <td>
                                    {item.inq_title}
                                </td>

                                <td>
                                    {item.inq_content}
                                </td>

                                <td>
                                    {item.inq_created?.split("T")[0]}
                                </td>

                            </tr>
                        ))}

                    </tbody>

                </table>

                {/* pagination */}

                <div className="pagination">
                    {/* 이전 */}
                    <button onClick={() => setInquiryPage(inquiryPage <= 1 ? inquiryTotalPages : inquiryPage - 1)}>
                        〈
                    </button>

                    {/* 페이지 번호 */}
                    {Array.from( {length:inquiryTotalPages},
                        (_, i) => (
                        <button key={i + 1}
                                className={ inquiryPage ===i + 1 ? "active" : ""}
                                onClick={() => setInquiryPage(i + 1)}>
                            {i + 1}
                        </button>
                    ))}

                    {/* 다음 */}
                    <button onClick={() => setInquiryPage(inquiryPage >= inquiryTotalPages  ? 1 : inquiryPage + 1)}>
                        〉
                    </button>

                </div>

            </>

        ) : (

            <p>
                작성한 문의사항이 없습니다.
            </p>
        )}

    </div>
)}

            {activeMenu === '회원 정보' && (
                <div className="card mypage-full-card">
                <h3>회원 정보 수정</h3>
                <p>아이디는 수정할 수 없습니다.</p>

                {/* [수정] 핸들러 추가 및 각 input 태그에 name, value, onChange 속성 바인딩 */}
                <form className="mypage-edit-form" onSubmit={handleUpdateSubmit}>
                    <label>아이디</label>
                    <input
                    name="mem_id"
                    value={editForm.mem_id}
                    readOnly
                    className="readonly-input"
                    />

                    <label>이름</label>
                    <input 
                    name="mem_name"
                    value={editForm.mem_name} 
                    onChange={handleInputChange}
                    />

                    <label>전화번호</label>
                    <input 
                    type='number'
                    name="mem_phone"
                    value={editForm.mem_phone} 
                    onChange={handleInputChange}
                    />

                    <label>닉네임</label>
                    <input 
                    name="mem_nickname"
                    value={editForm.mem_nickname} 
                    onChange={handleInputChange}
                    />

                    <button type="submit" className="btn-primary small">
                        수정 완료
                    </button>
                </form>
                </div>
            )}
            </section>
        </section>
     </main>
  );
}

export default MyPage;