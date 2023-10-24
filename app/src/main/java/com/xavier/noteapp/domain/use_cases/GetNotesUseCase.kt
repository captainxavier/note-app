package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.NoteRepository
import com.xavier.noteapp.domain.model.Note
import com.xavier.noteapp.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(orderType: OrderType = OrderType.Descending): Flow<List<Note>> = flow {
        when (orderType) {
            is OrderType.Descending -> {
                repository.getNotes().map { notes ->
                    notes.sortedByDescending { note -> note.createdAt }
                }.collect {
                    emit(it)
                }
            }

            is OrderType.Ascending -> {
                repository.getNotes().map { notes ->
                    notes.sortedBy { note -> note.createdAt }
                }.collect {
                    emit(it)
                }
            }
        }

    }
}