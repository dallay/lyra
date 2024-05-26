package com.lyra.app

import com.lyra.UnitTest
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Parameter
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors
import java.util.stream.Stream
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@UnitTest
internal class BeanValidationTest {
    @Test
    fun shouldHaveValidatedAnnotationForAllParameters() {
        controllers
            .stream()
            .filter { controller: Class<*> ->
                !EXCLUDED_CONTROLLERS.contains(
                    controller.simpleName,
                )
            }
            .flatMap(toMethods())
            .filter(visibleMethods())
            .filter(controllerMethods())
            .forEach(checkValidatedAnnotation())
    }

    private fun toMethods(): Function<Class<*>, Stream<Method>> {
        return Function { controller: Class<*> ->
            Arrays.stream(
                controller.methods,
            )
        }
    }

    private fun visibleMethods(): Predicate<Method> {
        return Predicate { method: Method ->
            !Modifier.isPrivate(
                method.modifiers,
            )
        }
    }

    private fun controllerMethods(): Predicate<Method> {
        return Predicate { method: Method ->
            !OBJECT_METHODS.contains(
                method,
            )
        }
    }

    private fun checkValidatedAnnotation(): Consumer<Method> {
        return Consumer<Method> { method: Method ->
            Arrays
                .stream(method.parameters)
                .filter { parameter: Parameter -> parameter.annotations.isNotEmpty() }
                .filter(checkedTypes())
                .forEach { parameter: Parameter ->
                    Assertions.assertThat(
                        Arrays.stream(
                            parameter.annotations,
                        ),
                    )
                        .`as`(errorMessage(method, parameter))
                        .anyMatch { annotation: Annotation -> annotation.annotationClass.java == Validated::class.java }
                }
        }
    }

    private fun errorMessage(method: Method, parameter: Parameter): String {
        return "Missing @Validated annotation in " +
            method.declaringClass.simpleName +
            " on method " +
            method.name +
            " parameter of type " +
            parameter.type.simpleName
    }

    private fun checkedTypes(): Predicate<Parameter> {
        return Predicate { parameter: Parameter ->
            val parameterClass = parameter.type
            !parameterClass.isPrimitive && parameterClass.name
                .startsWith(ROOT_PACKAGE)
        }
    }

    companion object {
        private const val ROOT_PACKAGE = "com.lyra"
        private val EXCLUDED_CONTROLLERS = setOf(
            "ExceptionTranslatorTestController",
            "AccountExceptionResource",
            "AuthenticationResource",
        )
        private val OBJECT_METHODS = Arrays.stream(
            Any::class.java.methods,
        ).collect(Collectors.toUnmodifiableSet())
        private val controllers = Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE))
                .setScanners(Scanners.TypesAnnotated, Scanners.SubTypes)
                .filterInputsBy(FilterBuilder().includePackage(ROOT_PACKAGE)),
        )
            .getTypesAnnotatedWith(RestController::class.java)
    }
}
