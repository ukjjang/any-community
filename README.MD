# 🧑‍🤝‍🧑 Any Community

- Any Community는 좋아요, 팔로우, 게시글 작성, 포인트 등 다양한 기능을 제공하는 커뮤니티 서비스입니다.
- 학습을 목적으로 진행한 프로젝트로, 사용하고 싶었던 기술과 기능을 적용한 개인 메모장과 같은 프로젝트입니다. 😊

---

### 스펙
* Kotlin 2.1.0
* JDK 21
* Gradle 8.10
* Spring Boot 3.3.5

### 세팅하기
- IntelliJ에서 JDK 21 설정
- ktlint pre-commit-hook 설정
    ```shell
    $ ./gradlew addKtlintFormatGitPreCommitHook
    ```

### 시작하기

> 최초 빌드시에는 이미지, 의존성 다운로드로 인해 시간이 다소 소요될 수 있습니다.
>
> Docker가 설치 및 실행 중이어야 합니다.

1. 인프라와 애플리케이션 함께 실행
    ```bash
    $ ./gradlew clean build -x test -x integrationTest # Build JAR without running tests
    $ docker compose up # Run infra with application (mvc-api, consumer)
    ```

2. 인프라만 실행 (IntelijJ에서 애플리케이션 실행하는 경우)
    ```bash
    $ docker compose -f docker-compose.yaml up # Run only infra
    # Run the application manually in IntelliJ
    ```

스웨거 접속
- http://localhost:8080/swagger-ui
