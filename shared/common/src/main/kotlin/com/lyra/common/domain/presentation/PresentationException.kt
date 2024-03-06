package com.lyra.common.domain.presentation

open class InvalidRequestException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)

class SortInvalidException(message: String? = null, cause: Throwable? = null) :
    InvalidRequestException(message, cause)

class FilterInvalidException(message: String? = null, cause: Throwable? = null) :
    InvalidRequestException(message, cause)
