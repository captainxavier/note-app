package com.xavier.noteapp.data.repository

import com.xavier.noteapp.data.database.dao.NotesDao
import com.xavier.noteapp.data.mapper.toDomainModel
import com.xavier.noteapp.data.mapper.toEntity
import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val notesDao: NotesDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = flow {
        val notes = notesDao.getNotes().map {
            it.toDomainModel()
        }
        emit(notes)
    }

    override suspend fun getNoteById(id: Int): Note {
        val note = notesDao.getNoteById(id = id)
        return note.toDomainModel()
    }

    override suspend fun upsertNote(note: Note) {
        notesDao.upsertNotes(noteEntity = note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(noteEntity = note.toEntity())
    }

    override suspend fun searchNotes(query: String): Flow<List<Note>> = flow {
        val notes = notesDao.getNotes().filter {
            it.title.contains(query, ignoreCase = true) || it.text.contains(
                query,
                ignoreCase = true
            )
        }.map { it.toDomainModel() }
        emit(notes)
    }
}