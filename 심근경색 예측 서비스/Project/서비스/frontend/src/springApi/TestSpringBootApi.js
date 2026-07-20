/* SpringBoot 백엔드 서버로 요청 처리하기 위한 페이지 */

/* 1. React 서버에서 -> SpringBoot 서버 간의 통신 처리
    - 프록시(Proxy) 처리 파일 호출  
    - axiosInstance.js 파일 생성 정의
      -> 해당 파일은 React와 SpringBoot서버 간의 통신에 사용되며,
         모든 React에서 공통으로 사용됨(하나만 만들어 놓으면 끝..)
      -> 해당 파일에는 SpringBoot 서버 또는 Python 서버 등
         백엔드 서버 연결 설정을 정의해 놓음
*/
import {springApi} from "../config/axiosInstance";


/* 2. SpringBoot 백엔드 서버로 요청하기 
    - 백엔드 서버로 요청을 위한 URL 패턴 정의 및 함수 정의
    - 함수 : getTest()
    - URL : http://localhost:8080/react/springboot_test
    - SpringBoot 백엔드 서버의 Controller 자바 클래스에서
        해당 URL 패턴에 맞는 메소드 호출 후 처리결과 리턴하여 응답
*/
export const getTest = () => 
    // 데이터 전송방식 : get 방식으로 호출
    //  - SpringBoot Controller에서는 getMapping()으로 받음
    //  - get(백엔드에 요청할 URL패턴)
    //  - 데이터 전송방식은 install로 설치한 axios 라이브러리에 있음
    springApi.get("react/springboot_test")