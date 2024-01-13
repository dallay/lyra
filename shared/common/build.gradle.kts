plugins {
    id("app.library.convention")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    testImplementation(libs.faker)
    testImplementation(libs.mockk)
}

testing {
    suites {
        // Configure the built-in test suite
        @Suppress("UnusedPrivateProperty")
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter(libs.versions.junit)
        }
    }
}
