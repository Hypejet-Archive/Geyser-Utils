plugins {
    id("java")
    id ("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "me.heroostech.floodgate"
version = "v1.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

dependencies {
    compileOnly(libs.lombok)
    compileOnly(libs.velocity)
    compileOnly(libs.floodgate)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.velocity)
    implementation(project(":api"))
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes ["Main-Class"] = "me.heroostech.floodgate.VelocityFloodgate"
        attributes ["Multi-Release"] = true
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("Floodgate-Minestom")
}