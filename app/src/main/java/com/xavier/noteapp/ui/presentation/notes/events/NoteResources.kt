package com.xavier.noteapp.ui.presentation.notes.events

sealed class NoteResources<T>(
    val data: T? = null,
    val errorMessage: String = ""
) {
    class Success<T>(data: T) : NoteResources<T>(data = data)
    class Failure(errorMessage: String = "") : NoteResources<String>(errorMessage)
    class Loading<T>(data: T? = null) : NoteResources<T>(data)

}
