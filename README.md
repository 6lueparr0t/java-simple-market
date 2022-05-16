# Java Board

## 특징

## 실행

- docker-compose.yml 을 먼저 실행한다.
  - mysql, adminer, java-board 가 있다. 
```shell
docker-compose -f docker-compose.yml up -d
```

## 기술 스택
- java 11.0.14 2022-01-18 LTS
- JAVA Spring Boot (v2.3.12.RELEASE)
- Gradle (v7.2)
- Mysql (v8.0.28)

## 테이블 설계
* **USER (TBL_USER)**

| 필드명      | 자료형         | 설명          |
|----------|-------------|-------------|
| **no**   | Integer     | Primary Key |
| **id**   | String(100) | 아이디         |
| **name** | String(100) | 이름          |
| **mail** | String(300) | e-mail      |
| **pswd** | String(100) | 패스워드        |
| **cdtm** | String      | 생성 날짜       |
