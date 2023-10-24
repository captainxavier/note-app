package com.xavier.noteapp.ui.presentation.composables

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isWideDisplay(activity: Activity = LocalContext.current as Activity): Boolean {
    val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = activity)
    val isWideDisplay: Boolean by remember {
        derivedStateOf {
            windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium
        }
    }

    return isWideDisplay

}