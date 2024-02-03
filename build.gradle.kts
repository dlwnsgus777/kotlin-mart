import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.2"
  id("io.spring.dependency-management") version "1.1.4"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  kotlin("jvm") version "1.9.22"
  kotlin("plugin.spring") version "1.9.22"
  kotlin("plugin.jpa") version "1.9.22"
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

group = "com.simple.mart"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}


val asciidoctorExt: Configuration by configurations.creating
val snippetsDir by extra { "build/generated-snippets" }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  /**
   * Rest Docs 설정
   */
  asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

// docs 설정 : https://velog.io/@chaerim1001/Spring-Rest-Docs-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-AsciiDoc-%EB%AC%B8%EB%B2%95  참고
// https://velog.io/@backtony/Spring-REST-Docs-%EC%A0%81%EC%9A%A9-%EB%B0%8F-%EC%B5%9C%EC%A0%81%ED%99%94-%ED%95%98%EA%B8%B0 참고

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs += "-Xjsr305=strict"
      jvmTarget = "17"
    }
  }

  test {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
  }

  withType<Test> {
    useJUnitPlatform()
  }

  asciidoctor {
    dependsOn(test)
    configurations("asciidoctorExt")
    inputs.dir(snippetsDir)
    baseDirFollowsSourceFile()
  }

  register<Copy>("copyDocs") {
    dependsOn(asciidoctor)
    from("${asciidoctor.get().outputDir}/index.html")
    into("src/main/resources/static/docs")
  }

  bootJar {
    dependsOn(asciidoctor)
    from("${asciidoctor.get().outputDir}/index.html") {
      into("static/docs")
    }
  }
}
