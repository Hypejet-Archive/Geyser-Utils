plugins {
    id("java")
    `maven-publish`
}

group = "me.heroostech.floodgate"
version = "v1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(libs.annotations)
    compileOnly(libs.lombok)
    compileOnly(libs.minestom)
    annotationProcessor(libs.lombok)
}


publishing {
    publications {
        create<MavenPublication>("minestom") {
            groupId = "me.heroostech.floodgate"
            artifactId = "MinestomFloodgate"
            version = "v1.0.0"
        }
    }
}