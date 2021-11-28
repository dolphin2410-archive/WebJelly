plugins {
    kotlin("jvm") version "1.6.0"
}

group = "io.github.dolphin2410"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

configurations {
    create("shade")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    compileOnly("org.eclipse.aether:aether-api:1.1.0")
    compileOnly("org.eclipse.aether:aether-spi:1.1.0")
    compileOnly("org.eclipse.aether:aether-impl:1.1.0")
    compileOnly("org.eclipse.aether:aether-connector-basic:1.1.0")
    compileOnly("org.eclipse.aether:aether-transport-file:1.1.0")
    compileOnly("org.eclipse.aether:aether-transport-http:1.1.0")
    compileOnly("org.apache.maven:maven-aether-provider:3.3.9")
    compileOnly("io.github.teamcheeze:jaw:1.0.4")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    jar {
        archiveFileName.set("webjelly.jar")
    }
}