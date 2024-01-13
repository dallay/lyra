import com.lyra.buildlogic.common.extensions.implementation

plugins {
    id("app.spring.boot.library.convention")
}

dependencies {
    implementation(project(":shared:common"))
    implementation(libs.spring.boot.starter.data.r2dbc)
    testImplementation(kotlin("test"))
    testImplementation(libs.faker)
    testImplementation(libs.mockk)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
