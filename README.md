# AWS_Free_Tier_Microservice
2024.09 - 2024.11
여행관리 앱을 위한 백엔드 서비스 구현

# 계획
AWS 프리티어를 통해 단 8시간만 운영되면 되는 서버를 구현 합니다.

기존 (전체 프리티어 서비스)
![구조도](img/AWS_service.png)


변경 (Docker-Compose)  
주요기술: Java, Spring Boot, Nginx, Docker, Docker-Compose
![구조도](img/service.png)

# 작업정보
## 작업기간
2024.12 - 2025.01(진행중)
## 작업인원
1명

# 실행방법
.env 파일 설정이 필요합니다.
```bash
   git clone https://github.com/jth0809/AWS_Free_Tier_Microservice.git
   cd AWS_Free_Tier_Microservice
   docker-compose up
```

### 향후방향
쿠버네티스 전환  
https://github.com/jth0809/Devops_Automation_K8s

캐시 서비스 제작

RabbitMQ로 전달된 내용의 검증

# 참고자료
모바일 데모 동영상:  
[![Video Label](http://img.youtube.com/vi/J1Fld_Swps8/0.jpg)](https://youtu.be/J1Fld_Swps8)  
https://youtu.be/J1Fld_Swps8

모바일 프로젝트 Github:  
https://github.com/1971309590/System-Project
