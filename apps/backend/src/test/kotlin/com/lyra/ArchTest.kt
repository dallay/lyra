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

    @BeforeEach
    fun setUp() {
        importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.lyra.app")
    }

    @Test
    fun domainShouldNotDependOnApplicationOrInfrastructure() {
        val boundedContexts = listOf("forms", "newsletter", "notifications", "organization", "team", "users")

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
                .because("El paquete 'domain' en '$context' no debería depender de 'application' ni 'infrastructure'")
                .check(importedClasses)
        }
    }

    @Test
    fun applicationShouldNotDependOnInfrastructure() {
        val boundedContexts = listOf("forms", "newsletter", "notifications", "organization", "team", "users")

        boundedContexts.forEach { context ->
            noClasses()
                .that()
                .resideInAnyPackage("com.lyra.app.$context.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.lyra.app.$context.infrastructure..")
                .because("El paquete 'application' en '$context' no debería depender de 'infrastructure'")
                .check(importedClasses)
        }
    }
}
