plugins {
    id("app.spring.boot.convention")
}

dependencies {
    implementation(project(":shared:common"))
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
