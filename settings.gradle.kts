@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("minestom", "com.github.Minestom:Minestom:-SNAPSHOT")
            library("lombok", "org.projectlombok:lombok:1.18.24")
            library("floodgate", "org.geysermc.floodgate:api:2.2.0-SNAPSHOT")
            library("velocity", "com.velocitypowered:velocity-api:3.1.1")
            library("annotations", "org.jetbrains:annotations:23.0.0")
        }
    }
}

rootProject.name = "Geyser-Utils"

include("api")
include("velocity")
include("minestom")
