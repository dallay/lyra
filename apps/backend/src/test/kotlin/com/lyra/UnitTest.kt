package com.lyra

import com.lyra.app.ReplaceCamelCase
import java.lang.annotation.Inherited
import org.junit.jupiter.api.DisplayNameGeneration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@DisplayNameGeneration(ReplaceCamelCase::class)
@Inherited
annotation class UnitTest
