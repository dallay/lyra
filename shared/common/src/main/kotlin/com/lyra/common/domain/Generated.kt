package com.lyra.common.domain

/**
 * Annotation to indicate that a class, function, or constructor has been generated.
 *
 * Use this annotation to mark generated code and provide information about the reason for generation.
 *
 * Example usage:
 *
 * ```kotlin
 * @Generated("Automatic code generation")
 * class MyClass {
 *     // class implementation
 * }
 * ```
 *
 * @param reason The reason for generating the code.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.CONSTRUCTOR)
annotation class Generated(val reason: String = "")
