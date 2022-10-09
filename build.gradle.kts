plugins {
    id("java")
}

allprojects {
    group = "me.heroostech.floodgate"
    version = "v1.0.0"
    description = "Floodgate for Minestom"
}

subprojects {
    apply {
        plugin("java")
    }
}
