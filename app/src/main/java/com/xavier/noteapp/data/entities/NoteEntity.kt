package com.xavier.noteapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime
@Entity(tableName = "note_entity")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val text:String,
    val createdAt:ZonedDateTime,
    @ColumnInfo(name = "title", defaultValue = "")
    val title:String,
)
