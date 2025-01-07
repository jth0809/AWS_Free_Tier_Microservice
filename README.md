# AWS_Free_Tier_Microservice
여행관리 앱을 위한 백엔드 서비스 구현

# 계획
AWS 프리티어를 통해 단 8시간만 운영되면 되는 서버를 구현 합니다.

하지만 하단에 기재한 문제로 변경 되었습니다.

기존
![구조도](img/AWS_service.png)


변경
주요기술: Java, Spring Boot, Nginx, Docker, Docker-Compose
![구조도](img/service.png)
# 진행과정
데이터가 아직 정해지지 않았기 때문에 먼저 각 서비스의 DB 테이블과 임의의 데이터를 설계합니다.

각 서비스의 DB를 스프링 데이터로 정의합니다.

Edge 서비스를 OAuth 클라이언트 서버로 지정하고 나머지 서비스를 OAuth 리소스서버로 지정합니다.

각 서비스를 연결하기 위해 Docker-Compose의 Docker-network로 DNS 설정을 합니다.

각 서비스의 스프링 시큐리티 정책을 정의합니다.

각 서비스의 application.yml에 ISSUER-URI, AWS sercret등을 설정하여 각 서비스 및 인증서버와 통신하고  
토큰을 검증하도록 합니다.

Nginx에서 HTTPS를 적용하고 Docker-network를 통해 Edge 서비스로 모든 통신이 전송되도록 합니다.  
(Edge는 스프링 게이트웨이 입니다.)

각 서비스의 컨트롤러에서 API를 작성합니다.

Docker-network를 통해 각 서비스를 연결합니다. (REST API, RabbitMQ(미완성))

### 향후방향
쿠버네티스 전환  
https://github.com/jth0809/Devops_Automation_K8s

캐시 서비스 제작

RabbitMQ로 전달된 내용의 검증

## 문제해결
AWS 프리티어의 EBS 볼륨 용량 제한 문제  

Docker-Compose / 온프레미스 환경으로 전환

모바일과 데이터 연동 문제

모바일 프론트엔드의 데이터구조 역추적으로 코드 및 데이터 구조 재구성

# 데모영상
https://youtu.be/J1Fld_Swps8