package com.xavier.noteapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xavier.noteapp.ui.presentation.base.BaseScreen
import com.xavier.noteapp.ui.presentation.notes.screens.CreateModeScreen
import com.xavier.noteapp.ui.presentation.notes.screens.EditModeScreen
import com.xavier.noteapp.ui.presentation.notes.screens.NoteScreen
import com.xavier.noteapp.ui.presentation.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    navType: NavType
) {
    NavHost(navController = navController, startDestination = NavItem.Home.route) {
        composable(NavItem.Home.route) {
            BaseScreen(navController = navController, navigationType = navType)
        }

        composable(NavItem.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(NavItem.Note.route + "/{note_id}") {
            NoteScreen(navController = navController, navigationType = navType)
        }

        composable(NavItem.CreateNotes.route) {
            CreateModeScreen(navController = navController, navigationType = navType)
        }
        composable(NavItem.EditNotes.route + "/{note_id}") {
            EditModeScreen(navController = navController, navigationType = navType)
        }
    }

}