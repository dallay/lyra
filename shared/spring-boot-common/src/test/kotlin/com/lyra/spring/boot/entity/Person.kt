package com.lyra.spring.boot.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table

@Table("persons")
data class Person(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var age: Int
)
