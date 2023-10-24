package com.xavier.noteapp.ui.presentation.notes.events

sealed class OrderEvent {
    object DescendingOrder : OrderEvent()
    object AscendingOrder : OrderEvent()
}
