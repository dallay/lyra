package com.lyra

import com.lyra.app.ReplaceCamelCase
import java.lang.annotation.Inherited
import org.junit.jupiter.api.DisplayNameGeneration
import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@DisplayNameGeneration(ReplaceCamelCase::class)
@Inherited
@ActiveProfiles("test")
annotation class UnitTest
