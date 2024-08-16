package com.lyra.app.authentication.infrastructure.cookie

import com.lyra.app.authentication.domain.error.MissingCookieException
import org.springframework.http.HttpCookie
import org.springframework.http.server.reactive.ServerHttpRequest

/**
 * Retrieves a cookie by name from the HTTP request.
 *
 * @param name The name of the cookie to retrieve.
 * @return The HttpCookie object.
 * @throws MissingCookieException if the cookie is not present in the request.
 */
fun ServerHttpRequest.getCookie(name: String): HttpCookie =
    cookies.getFirst(name) ?: throw MissingCookieException(name)
