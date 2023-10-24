package com.xavier.noteapp.ui.presentation.notes.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier.noteapp.domain.model.Note
import com.xavier.noteapp.domain.use_cases.CreateNoteUseCase
import com.xavier.noteapp.domain.use_cases.DeleteNoteUseCase
import com.xavier.noteapp.domain.use_cases.GetNoteByIdUseCase
import com.xavier.noteapp.domain.use_cases.GetNotesUseCase
import com.xavier.noteapp.domain.use_cases.SearchNoteUseCase
import com.xavier.noteapp.domain.utils.OrderType
import com.xavier.noteapp.domain.validators.NotesValidator
import com.xavier.noteapp.ui.presentation.notes.events.SaveNoteFormEvent
import com.xavier.noteapp.ui.presentation.notes.states.DeleteNotesState
import com.xavier.noteapp.ui.presentation.notes.states.NoteByIdState
import com.xavier.noteapp.ui.presentation.notes.states.NotesState
import com.xavier.noteapp.ui.presentation.notes.states.SaveNoteFormState
import com.xavier.noteapp.ui.presentation.notes.states.SaveNotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val searchNoteUseCase: SearchNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val noteValidator: NotesValidator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "NotesViewModel"
    }

    var state by mutableStateOf(SaveNoteFormState())


    private var noteId = mutableStateOf("0")
    private var titles = mutableListOf<String>()

    private val isEditMode = mutableStateOf(false)


    private val _stateGetNotes = MutableStateFlow(NotesState())
    val stateGetNotes = _stateGetNotes.asStateFlow()

    private val _stateNoteById = MutableStateFlow(NoteByIdState())
    val stateNoteById = _stateNoteById.asStateFlow()


    private val _stateSaveNotes = MutableSharedFlow<SaveNotesState>()
    val stateSaveNotes = _stateSaveNotes.asSharedFlow()

    private val _stateDeleteNotes = MutableSharedFlow<DeleteNotesState>()
    val stateDeleteNotes = _stateDeleteNotes.asSharedFlow()


    init {
        getNotes()
        savedStateHandle.get<String>("note_id")?.let { noteId ->
            Log.d(TAG, "init block: $noteId")
            this.noteId.value = noteId
            getNoteById(id = noteId.toInt())
        }
    }


    fun getNotes(orderType: OrderType = OrderType.Descending) {
        viewModelScope.launch {
            getNotesUseCase(orderType = orderType).collect { notes ->
                titles = notes.map { it.title }.toList().toMutableList()
                _stateGetNotes.value = NotesState(notes = notes)
                if (notes.isNotEmpty()) {
                    getNoteById(id = notes.first().id)
                }
            }
        }
    }

    fun searchNotes(query: String) {
        viewModelScope.launch {
            searchNoteUseCase(query = query).collect { notes ->
                _stateGetNotes.value = NotesState(notes = notes)
            }
        }
    }


    fun getNoteById(id: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(id = id).collect {
                _stateNoteById.value = NoteByIdState(note = it)
//                state = state.copy(
//                    title = it.title,
//                    notes = it.text
//                )
            }
        }
    }

    fun getNotesByIdEditMode() {
        viewModelScope.launch {
            getNoteByIdUseCase(id = noteId.value.toInt()).collect {
                state = state.copy(
                    title = it.title,
                    notes = it.text
                )
                isEditMode.value = true
            }
        }

    }


    fun onCreateNotesEvent(event: SaveNoteFormEvent) {
        when (event) {
            is SaveNoteFormEvent.TitleChanged -> {
                if (event.title.length <= 20) {
                    state = state.copy(title = event.title)
                }
            }

            is SaveNoteFormEvent.NotesChanged -> {
                state = state.copy(notes = event.notes)
            }

            is SaveNoteFormEvent.SubmitNotes -> {
                submitNotes()
            }

            else -> {}
        }
    }

    private fun submitNotes() {
        val titleResult = noteValidator.executeTitle(state.title)
        val noteResult = noteValidator.executeNotes(state.notes)


        val hasError = listOf(
            titleResult, noteResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                titleError = titleResult.errorMessage, notesError = noteResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            val title = state.title.ifEmpty { generateUniqueTitle(existingTitles = titles) }

//            val note = Note(
//                title = title,
//                text = state.notes,
//                createdAt = ZonedDateTime.now()
//            )

            val note =
                if (isEditMode.value) getExistingNote() else createNewNote(titleToUse = title)
            saveNotes(note)
        }
    }

    private suspend fun getExistingNote(): Note {
        val noteIdInt = noteId.value.toInt()
        val existingNote = getNoteByIdUseCase.invoke(id = noteIdInt).first()
        return existingNote.copy(
            title = state.title.ifEmpty { generateUniqueTitle(existingTitles = titles) },
            text = state.notes,
            createdAt = ZonedDateTime.now()
        )
    }

    private fun createNewNote(titleToUse: String): Note {
        return Note(
            title = titleToUse,
            text = state.notes,
            createdAt = ZonedDateTime.now()
        )
    }

    private fun saveNotes(note: Note) {
        viewModelScope.launch {
            _stateSaveNotes.emit(SaveNotesState(isLoading = true))
            try {
                createNoteUseCase(note = note)
                _stateSaveNotes.emit(SaveNotesState(message = "Note saved successfully"))
            } catch (e: Exception) {
                e.stackTrace
                _stateSaveNotes.emit(SaveNotesState(errorMessage = e.localizedMessage!!))
            }
        }
    }

    private fun generateUniqueTitle(existingTitles: List<String>): String {
        var newTitle = "New Title 1"
        var count = 1

        while (existingTitles.contains(newTitle)) {
            count++
            newTitle = "New Title $count"
        }

        return newTitle
    }

//

    fun deleteNote() {
        viewModelScope.launch {
            val existingNote = getNoteByIdUseCase.invoke(id = noteId.value.toInt()).first()
            _stateDeleteNotes.emit(DeleteNotesState(isLoading = true))
            try {
                deleteNoteUseCase(note = existingNote)
                _stateDeleteNotes.emit(DeleteNotesState(message = "Note Deleted successfully"))
            } catch (e: Exception) {
                e.stackTrace
                _stateDeleteNotes.emit(DeleteNotesState(errorMessage = e.localizedMessage!!))
            }
        }
    }

}