package com.lyra.spring.boot

import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.MediatorBuilder
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

@AutoConfiguration
open class LyraAutoConfiguration {
    @Bean
    open fun lyraSpringBeanProvider(applicationContext: ApplicationContext): LyraSpringBeanProvider =
        LyraSpringBeanProvider(applicationContext)

    @Bean
    open fun mediator(lyraSpringBeanProvider: LyraSpringBeanProvider): Mediator =
        MediatorBuilder(lyraSpringBeanProvider).build()
}
