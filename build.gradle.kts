import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.kotlin.plugin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.deps)
}

group = "club.anifox"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.serialization)

    implementation(libs.mapstruct)

    implementation(libs.jakarta.api)
    implementation(libs.jakarta.persistence)

    implementation(libs.springdoc.openapi.starter.webmvc.ui)

    implementation(libs.keycloak.admin.client)
    implementation(libs.keycloak.spring.boot)
    implementation(libs.keycloak.core)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.java)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.spring.boot.starter.data.jpa)
//    implementation(libs.spring.boot.starter.data.elasticsearch)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.oauth2.client)
    implementation(libs.spring.boot.starter.oauth2.resource.server)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.tomcat)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.jandex)
    implementation(libs.thumbnailator)
    implementation(libs.aws.java.sdk.s3)

    implementation(libs.postgresql)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.assertk)
}

tasks.getByPath("compileKotlin").dependsOn("ktlintFormat")
tasks.getByPath("runKtlintCheckOverTestSourceSet").dependsOn("ktlintFormat")

ktlint {
    android = false
    ignoreFailures = false

    reporters {
        reporter(ReporterType.PLAIN)
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
