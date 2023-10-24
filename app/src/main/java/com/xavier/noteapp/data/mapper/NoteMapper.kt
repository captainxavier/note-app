package com.xavier.noteapp.data.mapper

import com.xavier.noteapp.data.entities.NoteEntity
import com.xavier.noteapp.domain.model.Note

fun NoteEntity.toDomainModel(): Note {
    return Note(
        id = id,
        text = text,
        createdAt = createdAt,
        title=title
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        text = text,
        createdAt = createdAt,
        title = title
    )
}