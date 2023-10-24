package com.xavier.noteapp.nav

import androidx.annotation.DrawableRes
import com.xavier.noteapp.R

sealed class NavItem(val route: String, val title: String, @DrawableRes val icon: Int) {
    object Home : NavItem(route = "nav_home", title = "Home", icon = R.drawable.ic_home)
    object CreateNotes :
        NavItem(route = "nav_create_notes", title = "CreateNotes", icon = R.drawable.ic_home)

    object EditNotes :
        NavItem(route = "nav_edit_notes", title = "EditNotes", icon = R.drawable.ic_home)

    object Note : NavItem(route = "nav_note", title = "Note", icon = R.drawable.ic_home)
    object Profile : NavItem(route = "nav_profile", title = "Profile", icon = R.drawable.ic_person)

}
