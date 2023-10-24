package com.xavier.noteapp.data.repository

import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()
    override fun getNotes(): Flow<List<Note>> = flow {
        emit(notes)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun upsertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun searchNotes(query: String): Flow<List<Note>> = flow {
        val result = notes.filter {
            it.title.contains(query, ignoreCase = true) || it.text.contains(
                query,
                ignoreCase = true
            )
        }
        emit(result)
    }
}