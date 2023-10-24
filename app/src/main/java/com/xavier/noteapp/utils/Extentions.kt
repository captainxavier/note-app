package com.xavier.noteapp.utils


import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.xavier.noteapp.ui.presentation.base.DevicePosture
import com.xavier.noteapp.ui.presentation.base.isBookPosture
import com.xavier.noteapp.ui.presentation.base.isSeparating
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun WindowInfoTracker.getDevicePostureFlow(
    context: Context,
    lifecycle: Lifecycle
): Flow<DevicePosture> {
    return windowLayoutInfo(context as Activity)
        .flowWithLifecycle(lifecycle)
        .map { layoutInfo ->
            val foldingFeature =
                layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()
            when {
                isBookPosture(foldingFeature) ->
                    DevicePosture.BookPosture(foldingFeature.bounds)

                isSeparating(foldingFeature) ->
                    DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                else -> DevicePosture.NormalPosture
            }
        }
}

fun getTimeNow(dateTimeFormatter: String): String {
    val zdt = ZonedDateTime.now()
    val formatter = DateTimeFormatter.ofPattern(dateTimeFormatter)
        .withLocale(Locale.getDefault())
    return zdt.format(formatter)
}