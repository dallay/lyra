import com.lyra.buildlogic.common.extensions.implementation

plugins {
    id("app.spring.boot.convention")
}

dependencies {
    implementation(project(":shared:common"))
    implementation(libs.spring.boot.starter.data.r2dbc)
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
