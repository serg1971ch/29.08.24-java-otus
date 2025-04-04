rootProject.name = "otusJava"
include("hw01-gradle")


pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
    }
}
include("hw02-annotations")
include("hw03-testing")
include("hw04-streams")
include("hw05-threads")
include("hw06-iterator")
include("hw07-proxy")
include("hw08-jdbc")
include("hw09-https")
include("hw10-webServer")
include("hw11-spring")
include("hw11-spring")
