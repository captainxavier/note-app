package com.xavier.noteapp.nav

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.xavier.noteapp.ui.presentation.base.DevicePosture

enum class NavType {
    BOTTOM_NAVIGATION, HALF_NAVIGATION, PERMANENT_NAVIGATION_DRAWER
}

fun getNavigationType(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture
): NavType {
    val navigationType: NavType

    when (windowSize) {
        WindowWidthSizeClass.Medium -> {
            navigationType = NavType.HALF_NAVIGATION
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavType.HALF_NAVIGATION
            } else {
                NavType.PERMANENT_NAVIGATION_DRAWER
            }
        }

        else -> {
            navigationType = NavType.BOTTOM_NAVIGATION
        }
    }
    return navigationType
}