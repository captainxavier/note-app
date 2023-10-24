package com.xavier.noteapp.data.repository

import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun upsertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun searchNotes(query: String): Flow<List<Note>>
}