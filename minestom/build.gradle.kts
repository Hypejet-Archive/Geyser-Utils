plugins {
    id("java")
    id ("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "me.heroostech.floodgate"
version = "v1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.minestom)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(project(":api"))
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes ["Main-Class"] = "me.heroostech.floodgate.MinestomFloodgate"
        attributes ["Multi-Release"] = true
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
<<<<<<< HEAD
    archiveFileName.set("FloodgateVelocity-Minestom")
=======
    archiveFileName.set("FloodgateVelocity-Minestom.jar")
>>>>>>> b1e02cd (✏ Add Shadow Jar File Name)
}