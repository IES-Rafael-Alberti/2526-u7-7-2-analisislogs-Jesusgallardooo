plugins {
    application
    kotlin("jvm") version "2.3.0"
}

application {
    mainClass.set("org.iesra.MainKt")
}

group = "org.iesra"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}