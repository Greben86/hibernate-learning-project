plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "spring.hibernate.learning"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot and Hibernate"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
    modularity.inferModulePath = true
}

repositories {
	mavenCentral()
}

val jsonwebtokenVersion = "0.12.3"
val openApiVersion = "2.8.14"
val mapstructVersion = "1.6.3"
val mockitoVersion = "5.14.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(group = "io.jsonwebtoken", name = "jjwt-api", version = jsonwebtokenVersion)
    implementation(group = "io.jsonwebtoken", name = "jjwt-impl", version = jsonwebtokenVersion)
    implementation(group = "io.jsonwebtoken", name = "jjwt-jackson", version = jsonwebtokenVersion)
    implementation(group = "org.springdoc", name = "springdoc-openapi-starter-webmvc-ui", version = openApiVersion)
    implementation(group = "org.mapstruct", name = "mapstruct", version = mapstructVersion)
    compileOnly(group = "org.mapstruct", name = "mapstruct-processor", version = mapstructVersion)
    implementation("org.projectlombok:lombok")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("com.h2database:h2")

    annotationProcessor(group = "org.projectlombok", name = "lombok")
    annotationProcessor(group = "org.mapstruct", name = "mapstruct-processor", version = mapstructVersion)
}

tasks.withType<Test> {
	useJUnitPlatform()
}


