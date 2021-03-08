### spring security
### social login(naver, kakao, google) -> signup


#### application.yml 
```java

server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true

spring:
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/security?serverTimezone=Asia/Seoul
    username: {username}
    password: {password}


  jpa:
    hibernate:
      ddl-auto: create #create or update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true


  security:
    oauth2:
      client:
        registration:
          google: # /oauth2/authorization/google 이 주소로 동작함
            client-id: {clientId}
            client-secret: {clientSecret}
            scope: #가져올 유저 정보 스코프
              - email
              - profile

          # 네이버, 카카오는 OAuth2.0 공식 지원대상이 아니므로 provider 설정이 필요함
          naver:
            client-id: {clientId}
            client-secret: {clientSecret}
            scope:
              - name
              - email
            client-name: Naver
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/naver

          kakao:
            client-id: {clientId}
            client-secret: {clientSecret}
            scope:
              - profile
              - account_email
            client-name: Kakao # 클라이언트 네임은 대문자
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
            client-authentication-method: POST #카카오는 메소드를 포스트로 선언해줘야 함
        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id # 받아오는 json 가장 상단에 있는 값으로..
```
