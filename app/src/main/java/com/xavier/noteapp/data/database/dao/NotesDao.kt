package com.xavier.noteapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.xavier.noteapp.data.entities.NoteEntity

@Dao
interface NotesDao {
    @Upsert
    suspend fun upsertNotes(noteEntity: NoteEntity)

    @Query("SELECT * FROM note_entity")
    suspend fun getNotes():List<NoteEntity>

    @Query("SELECT * FROM note_entity WHERE id=:id")
    suspend fun getNoteById(id: Int):NoteEntity

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}