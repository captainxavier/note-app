package com.xavier.noteapp.ui.presentation.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xavier.noteapp.nav.NavItem
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.theme.Thistle35


@Composable
fun NavigationRailLayout(
    navController: NavController,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxHeight(),
    ) {
        AppNavigationRail(
            navController = navController,
        )
        content()
    }
}

@Composable
private fun AppNavigationRail(
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    val navSealedItems =
        listOf(NavItem.Home, NavItem.Profile)
    // Keep track of the selected item in the navigation drawer
    val selectedItem by rememberSaveable { mutableIntStateOf(0) }


    NavigationRail(
        modifier = modifier.fillMaxHeight(),
        header = {
            Spacer(modifier = Modifier.padding(8.dp))
            FloatingActionButton(onClick = {
                navController.navigate(NavItem.CreateNotes.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        navSealedItems.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = if (selectedItem == index) Thistle35 else MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    MyTextView(
                        text = item.title,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = if (selectedItem == index) Thistle35 else MaterialTheme.colorScheme.onPrimary
                    )
                },
            )
        }
    }
}