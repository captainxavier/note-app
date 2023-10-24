package com.xavier.noteapp.ui.presentation.notes.events

sealed class SaveNoteFormEvent {
    data class TitleChanged(val title: String) :
        SaveNoteFormEvent()

    data class NotesChanged(val notes: String) :
        SaveNoteFormEvent()

    object SubmitNotes :
        SaveNoteFormEvent()

}
