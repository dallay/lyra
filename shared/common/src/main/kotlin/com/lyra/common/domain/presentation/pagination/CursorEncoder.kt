package com.lyra.common.domain.presentation.pagination

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * This interface provides methods for encoding and decoding data using Base64.
 * It is annotated with @OptIn(ExperimentalEncodingApi::class) to opt-in to using the ExperimentalEncodingApi.
 *
 * @created 12/2/24
 */
@OptIn(ExperimentalEncodingApi::class)
interface CursorEncoder {
    /**
     * Encodes the given data into a Base64 string.
     *
     * @param data The data to encode.
     * @return The encoded data.
     */
    fun encode(data: String): String = Base64.encode(data.toByteArray())

    /**
     * Decodes the given Base64 string into data.
     * If the data is not a valid Base64 string, it throws an InvalidCursor exception.
     *
     * @param encodedData The data to decode.
     * @return The decoded data.
     * @throws [InvalidCursor] If the data is not a valid Base64 string.
     */
    fun decode(encodedData: String): String {
        @Suppress("TooGenericExceptionCaught")
        try {
            val decoded = Base64.decode(encodedData)
            return String(decoded)
        } catch (e: IndexOutOfBoundsException) {
            throw InvalidCursor("Invalid cursor", e)
        } catch (e: IllegalArgumentException) {
            throw InvalidCursor("Invalid cursor", e)
        }
    }
}

/**
 * This class provides an implementation of the CursorEncoder interface.
 * It uses the Base64 encoding and decoding methods provided by the interface.
 */
class Base64CursorEncoder : CursorEncoder
