# Java Board

## Feature

- JWT 로그인 (Mysql 에 Access Token 을 저장)
- 상품 조회
- 상품 주문
- 회원 주문 내역 조회

## Run

- Dockerfile 을 사용해 실행한다. 
```shell
# docker build
# -t : Tag
$ docker build -t 6lueparr0t/java-board .

# docker run
# -d : background 실행
# -p : 포트 포워딩
# --name : container 이름
$ docker run -d -p 8080:8080 --name java-board 6lueparr0t/java-board:latest
```

## Tech Stack
- [openjdk:11](https://hub.docker.com/_/openjdk)
- JAVA Spring Boot (Latest : v2.6.7)
- Gradle (v7.1)
- h2 (v1.4.200)

## DB Table
* **USER (TBL_USER)**

| 필드명        | 자료형         | 설명           |
|------------|-------------|--------------|
| **id**     | Integer     | Primary Key  |
| **name**   | String(200) | 이름           |
| **uid**    | String(200) | 아이디          |
| **pw**     | String(400) | 패스워드         |
| **email**  | String(400) | e-mail       |
| **cdtm**   | DateTime    | 생성 날짜        |
| **udtm**   | DateTime    | 수정 날짜        |
| **access** | String(400) | Access Token |
| **adtm**   | DateTime    | 접속 날짜        |

---

* **Order (TBL_ORDER)**

| 필드명      | 자료형      | 설명          |
|----------|----------|-------------|
| **id**   | Integer  | Primary Key |
| **uid**  | Long     | User ID     |
| **pno**  | Long     | Product ID  |
| **cdtm** | DateTime | 생성 날짜       |
| **udtm** | DateTime | 수정 날짜       |

---

* **Product (TBL_PRODUCT)**

| 필드명       | 자료형          | 설명           |
|-----------|--------------|--------------|
| **id**    | Integer      | Primary Key  |
| **name**  | String(400)  | 상품명          |
| **desc**  | String(4000) | 설명           |
| **price** | Long         | 가격           |
| **cdtm**  | DateTime     | 생성 날짜        |
| **udtm**  | DateTime     | 수정 날짜        |
