plugins {
    id("java")
    `maven-publish`
}

group = "me.heroostech.geyserutils"
version = "2.0"

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
        create<MavenPublication>("maven") {
            group = "me.heroostech.geyserutils"
            artifactId = "GeyserUtils"
            version = "2.0"
            from(components["java"])
        }
    }
}