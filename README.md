# pilot-login

개발바닥 유튜브를 보고 호돌맨님이 알려주신 파일럿 프로젝트를 토큰까지 구현해보고자 한다  

[호돌맨님 pilot-react](github.com/leejaycoke/pilot-react)
---

* 로그인 및 회원 정보 페이지를 구현합니다
* 서버에서 토큰 인증 및 사용자 정보를 반환합니다

## 요구 사항
* git(github)
* intellij
* 순수 javascript(es5 or es6)
* springboot(gradle, jdk 8 이상)
* Bearer 토큰
* TDD

## 구현 내용
화면 구성, 디자인은 자유

### 로그인 페이지
아이디, 비밀번호를 입력하고 로그인하는 화면을 만든다. 로그인이 완료되면 회원정보 페이지로 이동한다.

### 회원정보 페이지
회원정보를 보여주는 화면

### API
* 사용자 정보 클래스
* 클라이언트에서 아이디, 비밀번호 정보를 보내면 체크 후 토큰 생성 반환
* 아이디, 비밀번호 정보 없을 시 validation 체크 에러 반환(코드 및 메시지)
* 토큰 인증 validation
* 로그인 url(http://localhost:8080/auth/login) post method
* 회원정보 url(http://localhost:8080/v1/users/me) get method
* 로그아웃 url(http://localhost:8080/auth/logout) get method
* 서버 구현 TDD
