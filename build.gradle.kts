plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.spring"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // MySql
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql:1.0.0")

    implementation("org.flywaydb:flyway-core:11.8.0")
    implementation("org.flywaydb:flyway-mysql:11.8.0")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")

    implementation("io.r2dbc:r2dbc-proxy:1.1.5.RELEASE")

    // JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    // Jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // Health Check
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")

    // LOGGER
    implementation("org.slf4j:slf4j-api:2.0.13")

    // Mac OS
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.100.Final:osx-aarch_64") // Apple Silicon

    // TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("net.datafaker:datafaker:2.0.1")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
