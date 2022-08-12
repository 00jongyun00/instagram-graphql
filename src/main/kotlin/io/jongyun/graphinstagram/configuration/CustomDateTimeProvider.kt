package io.jongyun.graphinstagram.configuration

import java.time.OffsetDateTime
import java.time.temporal.TemporalAccessor
import java.util.*
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.stereotype.Component

@Component("dateTimeProvider")
class CustomDateTimeProvider : DateTimeProvider {

    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.of(OffsetDateTime.now())
    }
}