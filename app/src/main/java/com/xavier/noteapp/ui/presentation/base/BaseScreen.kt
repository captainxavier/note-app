package com.xavier.noteapp.ui.presentation.base

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.xavier.noteapp.domain.utils.OrderType
import com.xavier.noteapp.nav.NavItem
import com.xavier.noteapp.nav.NavType
import com.xavier.noteapp.ui.presentation.components.bottom_nav.BottomNavigationBar
import com.xavier.noteapp.ui.presentation.components.buttons.ActionButton
import com.xavier.noteapp.ui.presentation.components.scaffold.BaseScaffold
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.presentation.notes.screens.ListNotesScreen
import com.xavier.noteapp.ui.presentation.notes.screens.NoteScreen
import com.xavier.noteapp.ui.presentation.notes.view_models.NotesViewModel

@Composable
fun BaseScreen(
    navController: NavHostController,
    navigationType: NavType,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (navigationType == NavType.BOTTOM_NAVIGATION) {
            SingleNoteScreen(
                navController = navController
            )
        } else {
            DoubleNoteScreen(navigationType = navigationType)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleNoteScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val sortOrder = remember { mutableStateOf<OrderType>(OrderType.Descending) }

    BaseScaffold(
        topBar = {
            TopAppBar(
                title = {
                    MyTextView(
                        "Notes",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    ActionButton(
                        modifier = Modifier.padding(6.dp),
                        onClick = {
                            sortOrder.value =
                                if (sortOrder.value == OrderType.Ascending) OrderType.Descending else OrderType.Ascending
                            viewModel.getNotes(orderType = sortOrder.value)
                        },
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Filter Icon Button"
                    )
                }
            )
        },
        content = {
            ListNotesScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(it),
                onNoteClicked = { noteId ->
                    navController.navigate(NavItem.Note.route + "/${noteId}")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate(NavItem.CreateNotes.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note Icon")
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubleNoteScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navigationType: NavType
) {
    val activity = LocalContext.current as Activity
    val sortOrder = remember { mutableStateOf<OrderType>(OrderType.Descending) }
    TwoPane(
        first = {
            BaseScaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            MyTextView(
                                "Notes",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        actions = {
                            ActionButton(
                                modifier = Modifier.padding(6.dp),
                                onClick = {
                                    sortOrder.value =
                                        if (sortOrder.value == OrderType.Ascending) OrderType.Descending else OrderType.Ascending
                                    viewModel.getNotes(orderType = sortOrder.value)
                                },
                                imageVector = Icons.Default.Sort,
                                contentDescription = "Filter Icon Button"
                            )
                        }
                    )
                },
                content = {
                    ListNotesScreen(
                        onNoteClicked = { noteId ->
                            viewModel.getNoteById(id = noteId.toInt())
                        },
                        viewModel = viewModel,
                        modifier = Modifier.padding(it)
                    )
                }
            )
        },
        second = {
            NoteScreen(navigationType = navigationType)
        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
        displayFeatures = calculateDisplayFeatures(activity),
        foldAwareConfiguration = FoldAwareConfiguration.HorizontalFoldsOnly
    )
}


