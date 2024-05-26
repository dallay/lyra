package com.lyra.app.authentication.infrastructure

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@WithSecurityContext(factory = WithUnauthenticatedMockUserFactory::class)
annotation class WithUnauthenticatedMockUser

class WithUnauthenticatedMockUserFactory : WithSecurityContextFactory<WithUnauthenticatedMockUser> {

    override fun createSecurityContext(annotation: WithUnauthenticatedMockUser): SecurityContext =
        SecurityContextHolder.createEmptyContext()
}
