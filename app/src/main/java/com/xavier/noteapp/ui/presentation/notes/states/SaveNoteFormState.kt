package com.xavier.noteapp.ui.presentation.notes.states

data class SaveNoteFormState(
    var title: String = "",
    var titleError: String? = null,
    var notes: String = "",
    var notesError: String? = null,
)
