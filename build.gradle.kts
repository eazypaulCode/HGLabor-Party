plugins {
    java
}

group = "de.eazypaul"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://nexus.velocitypowered.com/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")
}