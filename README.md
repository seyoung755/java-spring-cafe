# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 프로젝트 소개

Springboot를 이용하여 간단한 게시판(카페)를 구현해보는 프로젝트입니다. 간단한 기능인 회원가입과 글쓰기부터 구현해나갑니다. 최대한 간단한 형태로 제작해보고자 처음에는 DB도 연결하지 않은 채 동작하도록
만들어봅니다. 이후에는 H2 Database를 통해 게시글을 저장하고 가입한 회원의 정보를 등록하도록 확장해나갑니다. DB 접근기술로는 JdbcTemplate를 활용하여 쿼리 작성을 연습해보았습니다. 프로젝트는
배포는 Heroku로 배포할 예정입니다.

### 배포 URL

https://codesquad-spring-cafe.herokuapp.com

## Step 03

### TO-DO

- [x] 테스트 코드 작성해보기
- [x] 코드 리뷰 피드백 사항 반영하기
- [x] 당장 필요없는 설계사항 들어내보기 (User의 id 값이라든지... )
    - userList의 #에 번호를 매기는 것을 id 없이 어떻게 보여줘야할 지 아이디어가 없어 우선 유지하기로 하였습니다.
- [x] 계층 간 데이터 모델에 대해서 학습해보기
- [x] 3단계 요구사항 적용해보기

--- 

## Step 04

### TO-DO

- [x] 코드 리뷰 피드백 사항 반영하기
   - [x] 배포와 로컬 환경을 구분하기 위한 profile 설정
   - [x] jdbc의 namedParameter 학습하기
   - [x] 회원가입 시 신규회원 검증 개선
   - [x] DDL에서 id 선언 시 불필요한 int(10) 선언 제거
   - [x] 테스트 코드 수정
- [x] 4단계 요구사항 적용해보기
- [x] 세션에 대해 학습해보기

### 로그인 시 예외 처리 과정
1. UserController에서 서비스 계층에 로그인 처리 요청을 보낸다.
2. 로그인 처리 과정에서 id나 비밀번호가 다를 경우 예외가 발생한다.
3. UserController 내부의 ExceptionHandler가 예외를 잡아채서 (어떻게 동작할까?) 처리 로직을 수행한다.
4. 로그로 예외 메시지를 띄우고 로그인 실패 페이지로 포워딩한다.
5. 포워딩할 때 Model에 기존에 입력했던 아이디 정보를 담아서 포워딩해준다.

### 회원정보 수정하기 
- [x] 메인 화면의 개인정보 수정을 누르면 내 개인정보 수정페이지로 간다. (세션 값 활용)
- [x] 모든 유저를 수정가능한 유저 목록의 개인정보 수정 버튼은 삭제한다. 
- [x] 현재 세션이 없거나, 세션과 다른 수정 페이지에 접속하려고 하면 에러페이지로 이동한다.
- [ ] 컨트롤러에 있는 검증 로직을 별도의 Validator를 이용해서 개선할 수 있을 것 같다.

### 궁금한 점

1. `User`의 비밀번호가 일치하는 지 검증하는 책임은 `User`의 도메인에서 지는 것이 맞을까요? 기존에는 Service에서 User에게 password가 일치하는 지 물어보고 다르면 `UserService`
   에서 예외를 발생시켰었는데요. 뭔가 검증결과를 받아서 예외를 발생시키는 책임은 `UserService`에 있다고 생각했었기 때문인데요. 수정한 부분은 바로 `User` 도메인에서 비밀번호가 다르면 예외를
   발생시키도록 했습니다. 굳이 도메인 내부에서 수행되는 검증 결과를 받아서 다시 `UserService`가 판단해서 예외를 발생시키는 것이 비효율적이라고 느껴져서 수정하게 되었습니다. 올바른 생각이었을지
   리뷰어님의 의견이 궁금합니다!

2. `UserController`에서 로그인 시도에 대한 예외처리를 하는 과정에서 Login 기능과 User를 가입/조회/수정하는 기능을 서로 다른 Controller로 분리하게 되었습니다. try-catch를
   이용해 메소드 단위로 예외처리하기보다는 Controller 단위로 ExceptionHandler를 사용하는 것이 바람직해보여서였는데요. 회원정보를 수정하는 과정에서도 비밀번호가 다르면 예외가 터지는데, 같은
   Controller에 있게 되면 로그인 실패와 동일한 처리로직을 따르게 되어서 분리하였습니다. 이런 식으로 관리하는 방법이 올바른지 확신이 안서서 의견을 여쭤보고자 질문드립니다. 

---
## Step 05: 게시글 권한부여

### TO-DO

- [x] 5단계 요구사항 적용해보기
  - [x] 비로그인 사용자는 글 목록만 볼 수 있다.
  - [x] 로그인한 사용자는 글을 작성/조회/삭제할 수 있다. (삭제 기능을 추가해야 한다.)
- [x] 게시글 작성 관련 기능 구현
  - [x] 게시글 작성 시 글쓴이 입력 필드를 삭제하고 세션에 저장된 유저 이름(name)을 입력한다.
    - [x] 글 목록에서 사용자 아이디를 누르면 프로필로 이동하는 기능 구현해보기
  - [x] 비로그인 사용자가 권한이 없는 페이지로 접속하면 로그인 페이지로 이동한다.
- [x] 게시글 수정 관련 기능 구현
  - [x] 게시글 수정 화면과 수정 요청은 로그인 사용자와 글쓴이 아이디가 같은 경우에만 가능하다.
  - [x] 다른 경우엔 에러 페이지를 출력한다.
- [x] 게시글 삭제 관련 기능 구현
  - [x] 삭제의 경우에도 게시글 수정과 같이 구현한다.
- [ ] 예외 처리 시 http stauts code 관리해보기 (현재는 500이 나온다)

### 고민
- Controller단에서는 검증이 가능한데 query로 직접 insert 가능한 예외에 대해서는 어떻게 처리해야 할까요?
ex) insert query로 존재하지 않는 사용자의 이름을 작성자로 하여 저장하는 경우

---
## Step 06: 댓글 

- [ ] 6단계 요구사항 적용해보기
  - [ ] 게시글 상세보기 화면에 댓글이 출력되고, 사용자가 댓글을 추가할 수 있다.
  - [ ] 자신이 쓴 댓글을 삭제할 수 있다.
- [ ] Reply 클래스를 통해 DB에 저장한다.
- [ ] Heroku로 배포한다.

- [ ] 추가 요구사항 적용해보기
  - [ ] 게시글을 삭제할 때 완전히 delete하지 않고, 상태를 변경한다.
  - [ ] 댓글이 달려있으면 삭제가 불가능하다.
  - [ ] 게시글 작성자와 댓글 작성자가 모두 같을 경우에만 삭제가 가능하다.

### 댓글 기능 구상
1. `Reply` 테이블에 FK로 article id를 가지고 있는다.
2. 게시글 페이지를 로딩할 때 Reply에서 article id로 검색하여 댓글을 가져온다.
3. 댓글을 페이지에 보여준다.
