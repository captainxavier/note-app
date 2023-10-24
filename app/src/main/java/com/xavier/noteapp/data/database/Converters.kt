package com.xavier.noteapp.data.database

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}