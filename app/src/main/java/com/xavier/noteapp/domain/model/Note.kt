package com.xavier.noteapp.domain.model

import java.time.ZonedDateTime

data class Note(
    val id:Int=0,
    val text:String,
    val createdAt: ZonedDateTime,
    val title:String
)
