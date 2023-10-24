package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.NoteRepository
import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(query:String):Flow<List<Note>> = flow {
        repository.searchNotes(query = query).collect{
            emit(it)
        }
    }
}