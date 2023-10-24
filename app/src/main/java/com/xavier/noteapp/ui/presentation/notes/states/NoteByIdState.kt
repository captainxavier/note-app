package com.xavier.noteapp.ui.presentation.notes.states

import com.xavier.noteapp.domain.model.Note

data class NoteByIdState(
    val note: Note?=null
)
