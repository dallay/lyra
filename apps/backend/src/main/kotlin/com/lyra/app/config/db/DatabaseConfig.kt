package com.lyra.app.config.db

import com.lyra.app.newsletter.subscriber.domain.SubscriberStatus
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.converter.SubscriberAttributesWriterConverter
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.converter.SubscriberConverter
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.converter.SubscriberStatusWriterConverter
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.postgresql.codec.EnumCodec.Builder.RegistrationPriority
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = ["com.lyra.*"])
@EnableR2dbcAuditing
class DatabaseConfig {
    /**
     * Use the customizer to add EnumCodec to R2DBC
     */
    @Bean
    fun connectionFactoryOptionsBuilderCustomizer(): ConnectionFactoryOptionsBuilderCustomizer {
        log.debug("Adding EnumCodec to R2DBC")
        return ConnectionFactoryOptionsBuilderCustomizer { builder: ConnectionFactoryOptions.Builder ->
            builder.option(
                Option.valueOf("extensions"),
                listOf(
                    EnumCodec.builder()
                        .withEnum("subscriber_status", SubscriberStatus::class.java)
                        .withRegistrationPriority(RegistrationPriority.FIRST)
                        .build(),
                ),
            )
        }
    }

    /**
     * Register converter to make sure Spring data treat enum correctly
     */
    @Bean
    fun r2dbcCustomConversions(databaseClient: DatabaseClient): R2dbcCustomConversions {
        log.debug("Registering custom converters to R2DBC")
        val dialect = DialectResolver.getDialect(databaseClient.connectionFactory)
        val converters: MutableList<Any?> = ArrayList(dialect.converters)
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS)
        return R2dbcCustomConversions(
            CustomConversions.StoreConversions.of(dialect.simpleTypeHolder, converters),
            listOf(
                SubscriberConverter(),
                SubscriberStatusWriterConverter(),
                SubscriberAttributesWriterConverter(),
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(DatabaseConfig::class.java)
    }
}
