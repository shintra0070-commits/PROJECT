/* 
    MemberForm.js 
     - 상세보기, 신규입력, 수정 시에 공통으로 사용할 페이지
     - mode 상태변수를 선언 및 정의하여 
       -> 상태(상세보기 or 신규입력 or 수정)에 따라 
       -> style(CSS)을 이용하여 제어 처리..
*/
import React from "react";


const MemberForm = ({member, handleChange, handleCancel, handleSubmit, mode}) => {

    // mode 상태에 따라 input type의 readOnly True or False 제어
    //  - readOnly : 읽기전용(true), 쓰기가능(false)
    const isReadOnly = (field) => {
        // 상세보기(view)는 모두 읽기 전용으로 처리
        if(mode === "view") return true;

        if(field === "mem_name" || field === "mem_id") return true;

        if(mode === "edit" && field === "mem_id") return true;

        // 이외 모든 처리 (저장(add))는 쓰기 전용으로
        return false;
    };

        const setBackGroundColor = (field) => {

        if(mode === "view") return "darkgrey";
        // 수정(edit)인 경우에는 회원 이름을 제외한 나머지는 모두 읽기 전용으로 처리
        if(field === "mem_name" || field === "mem_id") return "darkgrey";    

        if(field === "mem_name" || field === "mem_id") return "darkgrey";

        if(mode === "edit" && field === "mem_id")
            return "darkgrey";

        return "white";
    };

    return(
        <form onSubmit={handleSubmit}>
            {/* 회원 아이디 입력 폼 정의 */}
            {/* <div>
                <label>회원 이메일 : </label>
                <input type="email" name="mem_id" value={member.mem_id || member.mem_email}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_id")}
                        style={{backgroundColor : setBackGroundColor("mem_id")}} />
            </div> */}

            {/* 회원 이름 입력 폼 정의 */}
            {/* <div>
                <label>회원 이름 : </label>
                <input type="text" name="mem_name" value={member.mem_name || ""}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_name")}
                        style={{backgroundColor : setBackGroundColor("mem_name")}} />
            </div> */}

            {/* 회원 이메일 입력 폼 정의 */}
            {/* <div>
                <label>회원 전화번호 : </label>
                <input type="text" name="mem_phone" value={member.mem_phone || ""}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_phone")}
                        style={{backgroundColor : setBackGroundColor("mem_phone")}} />
            </div> */}


            {/* <div>
                <label>회원 닉네임: </label>
                <input type="text" name="mem_nickname" value={member.mem_nickname || ""}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_nickname")}
                        style={{backgroundColor : setBackGroundColor("mem_nickname")}} />
            </div> */}

            <div>
                <label>회원 이메일 : </label>
                <input type="email" name="mem_id" value={member.mem_id || member.mem_email}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_id")} />
            </div>

            {/* 회원 이름 입력 폼 정의 */}
            <div>
                <label>회원 이름 : </label>
                <input type="text" name="mem_name" value={member.mem_name || ""}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_name")} />
            </div>

            {/* 회원 이메일 입력 폼 정의 */}
            <div>
                <label>회원 전화번호 : </label>
                <input type="number" name="mem_phone" value={member.mem_phone || ""}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_phone")} />
            </div>


            <div>
                <label>회원 닉네임: </label>
                <input type="text" name="mem_nickname" value={member.mem_nickname || ""}
                        onChange={handleChange}
                        readOnly={isReadOnly("mem_nickname")} />
            </div>


            <hr/>
            {/* 저장 및 취소 버튼 */}
            {/* mode값이 view가 아닌 경우(수정/입력)에만 활성화하기 */}
            {mode !== "view" && (
                <div>
                    {/* 저장 버튼 */}
                    <button type="submit">저 장</button>

                    {/* 취소 버튼 */}
                    <button type="button" 
                            onClick={(e) => handleCancel(e)}>취 소</button>
                </div>
            )}
        </form>
    );
};

export default MemberForm;