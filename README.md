# 📝 실행 방법 및 환경설정 가이드 (README)

---
<br>

## 📌 프로젝트 실행 방법

이 문서는 **온라인 도서 관리 시스템**을 실행하는 방법과 환경설정 파일(`application.yml`, `application-production.yml`)을 관리하는 방법을 설명합니다.

---
<br>

## ⚙️ 1. 환경 설정 파일 (YAML)
본 프로젝트는 **Spring Boot** 기반으로 개발되었으며, 환경에 따라 설정 파일을 분리하여 관리합니다.

### 🔹 환경별 설정 파일
- `presentation/src/main/resources/application.yml` → 기본 환경 설정 (공통)
- `presentation/src/main/resources/application-production.yml` → 운영 환경 설정

---
<br>

## 🛠 2. 실행 전 필수 환경 설정

### 📌 (1) `application.yml` (공통 설정)
📍 **경로:** `presentation/src/main/resources/application.yml`

```yaml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true

spring:
  profiles:
    active: production
    group:
      local: local
      production: production

  cache:
    type: redis

jwt:
  secret: YOUR_SECRET_KEY
  accessTokenExpiration: 3600000
  refreshTokenExpiration: 2592000000

---
cloud:
  aws:
    region:
      static: ap-northeast-2
    stack:
      auto: false

logging:
  level:
    com:
      amazonaws:
        util:
          EC2MetadataUtils: error
  pattern:
    dateformat: yyyy-MM-dd HH:mm:ss.SSSz,Asia/Seoul

---
spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

---
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

---
<br>

### 📌 (2) `application-production.yml` (운영 환경)
📍 **경로:** `presentation/src/main/resources/application-production.yml`

```yaml
spring:
  config:
    activate:
      on-profile: production

  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://43.203.56.163:3306/assignment?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&
    username: root
    password: assignment1!

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
        highlight_sql: true

  data:
    redis:
      host: 43.203.56.163
      port: 6379
      ssl:
        enabled: false
```

---
<br>

## 🛠 3. Swagger API 문서 확인
- API 명세서: http://localhost:8080/swagger-ui/index.html#/

---
