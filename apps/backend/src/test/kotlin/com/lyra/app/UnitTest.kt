package com.lyra.app

import java.lang.annotation.Inherited
import org.junit.jupiter.api.DisplayNameGeneration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@DisplayNameGeneration(ReplaceCamelCase::class)
@Inherited
annotation class UnitTest
