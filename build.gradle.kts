plugins {
    kotlin("jvm") version "1.6.21"
}

group = "me.dolphin2410"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    compileOnly("org.eclipse.aether:aether-api:1.1.0")
    compileOnly("org.eclipse.aether:aether-spi:1.1.0")
    compileOnly("org.eclipse.aether:aether-impl:1.1.0")
    compileOnly("org.eclipse.aether:aether-connector-basic:1.1.0")
    compileOnly("org.eclipse.aether:aether-transport-file:1.1.0")
    compileOnly("org.eclipse.aether:aether-transport-http:1.1.0")
    compileOnly("org.apache.maven:maven-aether-provider:3.3.9")
    compileOnly("io.github.teamcheeze:jaw:1.0.4")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
}

tasks {
    jar {
        archiveFileName.set("webjelly.jar")
    }
}