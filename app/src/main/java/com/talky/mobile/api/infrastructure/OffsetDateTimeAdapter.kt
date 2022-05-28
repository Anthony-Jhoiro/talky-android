package com.talky.mobile.api.infrastructure

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.JsonToken.NULL
import java.io.IOException
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeAdapter(private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME) : TypeAdapter<OffsetDateTime>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: OffsetDateTime?) {
        if (value == null) {
            out?.nullValue()
        } else {
            out?.value(formatter.format(value))
        }
    }

    @Throws(IOException::class)
    override fun read(out: JsonReader?): OffsetDateTime? {
        out ?: return null

        when (out.peek()) {
            NULL -> {
                out.nextNull()
                return null
            }
            else -> {
                return ZonedDateTime.parse(out.nextString().split(".")[0], formatter).toOffsetDateTime()
            }
        }
    }
}
