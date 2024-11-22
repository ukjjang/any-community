# Any Community

- 커뮤니티 서비스!

---

### 개발 환경
* Kotlin 2.0.20
* JDK 21
* Gradle 8.10
* Spring Boot 3.3.5
* JPA / Hibernate
* Kotlin JDSL

### 세팅하기
- IntelliJ에서 JDK 21 설정
- ktlint pre-commit-hook 설정
    ```shell
    $ ./gradlew addKtlintFormatGitPreCommitHook
    ```

### 시작하기

```bash
$ ./gradlew build
$ docker compose up
```

- http://localhost:8080/swagger-ui
