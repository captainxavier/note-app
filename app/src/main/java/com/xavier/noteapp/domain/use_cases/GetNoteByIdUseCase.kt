package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.NoteRepository
import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(id: Int): Flow<Note> = flow {
        val response = repository.getNoteById(id = id)
        if (response != null) {
            emit(response)
        }
    }
}