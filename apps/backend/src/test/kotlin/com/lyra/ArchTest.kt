package com.lyra

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class ArchTest {

    private lateinit var importedClasses: JavaClasses
    private val boundedContexts = listOf(
        "forms",
        "newsletter.subscriber",
        "newsletter.tag",
        "notifications",
        "organization",
        "team",
        "users",
    )

    @BeforeEach
    fun setUp() {
        importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.lyra.app")
    }

    @Test
    fun domainShouldNotDependOnApplicationOrInfrastructure() {

        boundedContexts.forEach { context ->
            noClasses()
                .that()
                .resideInAnyPackage("com.lyra.app.$context.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                    "com.lyra.app.$context.application..",
                    "com.lyra.app.$context.infrastructure..",
                )
                .because("The 'domain' package in '$context' should not depend on 'application' or 'infrastructure'")
                .check(importedClasses)
        }
    }

    @Test
    fun applicationShouldNotDependOnInfrastructure() {

        boundedContexts.forEach { context ->
            noClasses()
                .that()
                .resideInAnyPackage("com.lyra.app.$context.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.lyra.app.$context.infrastructure..")
                .because("The 'application' package in '$context' should not depend on 'infrastructure'")
                .check(importedClasses)
        }
    }
}
