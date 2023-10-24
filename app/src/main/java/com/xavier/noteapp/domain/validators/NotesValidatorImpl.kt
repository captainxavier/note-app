package com.xavier.noteapp.domain.validators

class NotesValidatorImpl :NotesValidator {
    override fun executeTitle(title: String): ValidationResult {
        return ValidationResult(
            successful = true
        )
    }

    override fun executeNotes(notes: String): ValidationResult {
        if (notes.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Notes cannot be empty!"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}