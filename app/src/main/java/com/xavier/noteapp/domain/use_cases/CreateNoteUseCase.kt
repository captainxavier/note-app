package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.NoteRepository
import com.xavier.noteapp.domain.model.Note
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.upsertNote(note = note)
    }
}

