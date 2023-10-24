package com.xavier.noteapp.domain.validators

interface NotesValidator {
    fun executeTitle(title:String):ValidationResult
    fun executeNotes(notes:String):ValidationResult
}