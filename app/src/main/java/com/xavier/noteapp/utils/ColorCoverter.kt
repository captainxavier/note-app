package com.xavier.noteapp.utils

import androidx.compose.ui.graphics.Color

fun colorToLong(color: Color): Long {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()
    val alpha = (color.alpha * 255).toInt()

    return (alpha.toLong() shl 24) or ((red shl 16).toLong()) or ((green shl 8).toLong()) or blue.toLong()
}