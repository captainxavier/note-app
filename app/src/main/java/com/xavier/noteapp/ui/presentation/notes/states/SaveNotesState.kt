package com.xavier.noteapp.ui.presentation.notes.states

data class SaveNotesState(
    val message: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
