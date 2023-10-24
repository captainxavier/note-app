package com.xavier.noteapp.ui.presentation.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.xavier.noteapp.nav.NavGraph
import com.xavier.noteapp.nav.NavType
import com.xavier.noteapp.ui.presentation.base.BottomBarLayout
import com.xavier.noteapp.ui.presentation.base.NavigationRailLayout

@Composable
fun AdaptiveUiApp(
    navigationType: NavType,
    navController: NavHostController
) {
    if (navigationType == NavType.PERMANENT_NAVIGATION_DRAWER) {
        // Render a permanent navigation drawer
        NavigationRailLayout(
            navController = navController,
            content = {
                NavGraph(navController = navController, navigationType)
            }
        )
    } else {
        BottomBarLayout(navController = navController, content = {
            NavGraph(navController = navController, navigationType)
        })
    }

}