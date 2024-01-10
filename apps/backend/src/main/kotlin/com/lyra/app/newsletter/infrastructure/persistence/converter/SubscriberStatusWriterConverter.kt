package com.lyra.app.newsletter.infrastructure.persistence.converter

import com.lyra.app.newsletter.domain.SubscriberStatus
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport

@WritingConverter
class SubscriberStatusWriterConverter : EnumWriteSupport<SubscriberStatus>()
