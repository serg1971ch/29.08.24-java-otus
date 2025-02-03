plugins {
    id("java")
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly("javax.servlet:javax.servlet-api:3.0.1")

    implementation("org.slf4j:slf4j-api:2.0.16")
    testImplementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("org.apache.maven:maven-plugin-api:2.2.1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}