# 여행 관리 앱 백엔드 서비스 (AWS Free Tier 최적화)

[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)](https://www.nginx.com/)
[![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

여행 일정을 관리하고 공유하는 모바일 앱을 위한 백엔드 서비스입니다.

<br>

## 목차 (Table of Contents)

1.  [프로젝트 소개](#1-프로젝트-소개)
2.  [아키텍처 개선 과정](#2-아키텍처-개선-과정)
3.  [기술 스택](#3-기술-스택)
4.  [실행 방법](#4-실행-방법)
5.  [향후 개선 계획](#5-향후-개선-계획)
6.  [관련 링크](#6-관련-링크)

<br>

## 1. 프로젝트 소개

-   **프로젝트 기간**: 2024.09 ~ 2024.11
-   **리팩토링 기간**: 2024.12 ~ 2025.01 (쿠버네티스 전환)
-   **작업 인원**: 1명
-   **핵심 목표**: AWS 프리티어 한도 내에서 최소한의 비용으로 운영 가능하며, 하루 중 특정 시간(8시간)만 동작하는 서버를 구현합니다.

<br>

## 2. 아키텍처 개선 과정

이 프로젝트의 핵심은 초기 클라우드 기반 아키텍처의 현실적인 문제점을 파악하고, Docker-Compose를 활용하여 개선한 것입니다.

### AS-IS (초기 구성)
초기에는 다양한 AWS 관리형 서비스를 활용하여 시스템을 구성했습니다.

![초기 구조도](img/AWS_service.png)

-   **문제점**: 필요한 애플리케이션들을 구동하기 위해 EC2 인스턴스를 구성하는 과정에서, AWS 프리티어에서 제공하는 EBS(Elastic Block Store) 기본 용량을 초과하여 예상치 못한 비용이 발생하는 문제를 발견했습니다.

### TO-BE (개선 후 구성)
비용 및 환경 문제를 해결하고, 로컬 환경에서 전체 시스템을 비슷하게 구현하기 위해 Docker-Compose 방식으로 전환했습니다.

![개선 후 구조도](img/service.png)

-   **개선 효과**: docker-compose up 명령어 하나로 전체 시스템을 실행하고, 비용 부담 없이 자유롭게 개발 및 테스트를 진행할 수 있게 되었습니다.

<br>

## 3. 기술 스택

| 구분 | 기술 |
|---|---|
| **Backend** | `Java`, `Spring Boot` |
| **DevOps** | `Docker`, `Docker-Compose` |
| **Reverse Proxy** | `Nginx` |
| **Cache** | `Redis` |
| **Message Queue**| `RabbitMQ` |

<br>

## 4. 실행 방법

**사전 준비**: 프로젝트 루트 디렉토리에 `.env` 파일을 생성하고, 아래 내용을 채워야 합니다.
```env
# 예시
# AWS Cognito Configuration
ISSUER_URI=your-cognito-issuer-uri
AWS_CLIENT_ID=your-client-id
AWS_CLIENT_SECRET=your-secret-key
```

**실행 명령어**:
```bash
# 1. Git 저장소 복제
git clone https://github.com/jth0809/AWS_Free_Tier_Microservice.git

# 2. 프로젝트 디렉토리로 이동
cd AWS_Free_Tier_Microservice

# 3. Docker Compose 실행
docker-compose up --build
```

<br>

## 5. 향후 개선 계획

-   **Kubernetes 전환**: `docker-compose` 환경을 넘어, 더 높은 수준의 오케스트레이션과 확장성을 확보하기 위해 Kubernetes 기반으로 전환할 계획입니다. ([관련 저장소](https://github.com/jth0809/Devops_Automation_K8s))
-   **메시지 큐(RabbitMQ) 고도화**: 현재 구상 중인 메시지 기반 비동기 처리의 안정성을 높이기 위해, 전달된 내용에 대한 검증 로직을 추가하고 모니터링 시스템을 연동할 계획입니다.

<br>

## 6. 관련 링크

-   **🎥 모바일 앱 데모 영상**: [영상 보러가기](https://youtu.be/J1Fld_Swps8)
-   **📱 모바일 앱 GitHub**: [https://github.com/1971309590/System-Project](https://github.com/1971309590/System-Project)
