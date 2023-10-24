package com.xavier.noteapp.ui.presentation.components.bottom_nav

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xavier.noteapp.nav.NavItem
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.theme.Thistle35


@Composable
fun BottomNavigationBar(navController: NavController) {
    val navSealedItems =
        listOf(NavItem.Home, NavItem.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(0) }


    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        navSealedItems.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = if (selectedItem == index) Thistle35 else MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    MyTextView(
                        item.title,
                        color =if (selectedItem == index) Thistle35 else MaterialTheme.colorScheme.onPrimary
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                    }
                },

                )
        }
    }


}