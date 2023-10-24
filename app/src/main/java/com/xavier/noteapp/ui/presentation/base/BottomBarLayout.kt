package com.xavier.noteapp.ui.presentation.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.xavier.noteapp.ui.presentation.components.scaffold.BaseScaffold

@Composable
fun BottomBarLayout(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit,
) {
    BaseScaffold(
        content = content
    )
}