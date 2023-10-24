package com.xavier.noteapp.domain.validators

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:String?=null
)
