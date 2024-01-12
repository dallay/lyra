package com.lyra.app.newsletter.application

import com.lyra.common.domain.bus.query.Response

data class SubscribersResponse(val subscribers: List<SubscriberResponse>) : Response

data class SubscriberResponse(
    val id: String,
    val email: String,
    val name: String,
    val status: String,
) : Response
