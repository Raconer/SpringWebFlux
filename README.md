# Spring WebFlux Kotlin 프로젝트

## 📃 개요

Spring WebFlux 기반 Kotlin 프로젝트입니다.
`@RestController` 방식과 `RouterFunction`/
`HandlerFunction`을 구분해 구현했으며,
R2DBC를 활용하여 비동기 데이터 연동을 진행합니다.
---

## 🛠️ 기술 사용

| 구분            | 사용 기술                          |
| ------------- | ------------------------------ |
| Language      | Kotlin 1.9                     |
| Web Framework | Spring WebFlux                 |
| Build Tool    | Gradle (Kotlin DSL)            |
| DB            | MySQL + R2DBC                  |
| Migration     | Flyway                         |
| JSON          | kotlinx.serialization          |
| Security      | Jasypt                         |
| API Docs      | springdoc-openapi (Swagger UI) |
| Health Check  | Spring Boot Actuator           |
| Logging       | SLF4J                          |
| Testing       | JUnit5, DataFaker              |

---

## 프로젝트 탐색

```
src/main/kotlin/com/spring/webflux
├── content
│   ├── controller/v1        ← @RestController 방식
│   ├── controller/v2
│   │   ├── handler          ← HandlerFunction
│   │   └── router           ← RouterFunction
│   └── service              ← 비즈니스 로집
├── repository               ← R2DBC Repository
├── common/domain            ← Entity 정의
├── config                   ← Swagger, R2DBC, Auditing 설정
└── SpringWebFluxApplication.kt
```

---

## 프리터뷰

### Content API

* `GET /v1/content`:
  * `@RestController` 방식
* `GET /v2/content`:
  * `RouterFunction` + `HandlerFunction` 함수형 로용트

---
Swagger UI:
[http://localhost:8080/webjars/swagger-ui/index.html](http://localhost:8080/webjars/swagger-ui/index.html)
---
