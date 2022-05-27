package com.talky.mobile.api.infrastructure

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Serializer {
    private const val datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    private val formatter = DateTimeFormatter.ofPattern(datePattern).withZone(ZoneId.systemDefault())

    @JvmStatic
    val gsonBuilder: GsonBuilder = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdapter(formatter))
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter(formatter))
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter(formatter))
        .registerTypeAdapter(ByteArray::class.java, ByteArrayAdapter())

    @JvmStatic
    val gson: Gson by lazy {
        gsonBuilder.create()
    }
}
