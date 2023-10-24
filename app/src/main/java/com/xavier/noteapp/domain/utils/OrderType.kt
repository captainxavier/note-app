package com.xavier.noteapp.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
