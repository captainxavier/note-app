package com.xavier.noteapp.ui.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowInfoTracker
import com.xavier.noteapp.nav.getNavigationType
import com.xavier.noteapp.ui.presentation.base.DevicePosture
import com.xavier.noteapp.ui.presentation.composables.AdaptiveUiApp
import com.xavier.noteapp.ui.theme.NoteAppTheme
import com.xavier.noteapp.utils.getDevicePostureFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
        class MainActivity : ComponentActivity() {
            @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                val windowSize = calculateWindowSizeClass(this).widthSizeClass
                val devicePosture = getDevicePosture().collectAsState().value
                val navigationType = getNavigationType(windowSize, devicePosture)
                val navController = rememberNavController()

                AdaptiveUiApp(navigationType = navigationType, navController = navController)

            }
        }
    }

    private fun getDevicePosture(): StateFlow<DevicePosture> {
        return WindowInfoTracker.getOrCreate(this)
            .getDevicePostureFlow(this, lifecycle)
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
    }
}