plugins {
    java
    id("xyz.jpenilla.run-velocity") version "2.0.0"
}

group = "de.eazypaul"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

tasks {
    runVelocity {
        velocityVersion("3.1.1")
    }
}
