package com.xavier.noteapp.ui.presentation.notes.states

import com.xavier.noteapp.domain.model.Note

data class NotesState(val notes: List<Note>? = emptyList())
