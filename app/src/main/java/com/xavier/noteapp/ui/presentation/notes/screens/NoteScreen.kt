package com.xavier.noteapp.ui.presentation.notes.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xavier.noteapp.nav.NavItem
import com.xavier.noteapp.nav.NavType
import com.xavier.noteapp.ui.presentation.components.buttons.ActionButton
import com.xavier.noteapp.ui.presentation.components.card.NotesDetailCard
import com.xavier.noteapp.ui.presentation.components.dialog.DeleteDialog
import com.xavier.noteapp.ui.presentation.components.dialog.LoadingDialog
import com.xavier.noteapp.ui.presentation.components.scaffold.BaseScaffold
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.presentation.notes.view_models.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
    navigationType: NavType,
    navController: NavController = rememberNavController()
) {

    val TAG = "NoteScreen"
    val noteState = viewModel.stateNoteById.collectAsStateWithLifecycle().value
    val note = noteState.note

    val context = LocalContext.current

    val isLoading = remember {
        mutableStateOf(false)
    }

    val isDeleteDialog = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = viewModel.stateDeleteNotes) {
        viewModel.stateDeleteNotes.collect {
            isLoading.value = it.isLoading
            if (!it.message.isNullOrBlank()) {
                Log.d(TAG, "EditModeScreen: ${it.message}")
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                navController.navigate(NavItem.Home.route)
            }
            if (it.errorMessage.isNotEmpty()) {
                Log.e(TAG, "EditModeScreen: ${it.errorMessage}")
            }
        }
    }

    if (isLoading.value) {
        LoadingDialog()
    }

    if (isDeleteDialog.value) {
        DeleteDialog(openDialog = {

            isDeleteDialog.value = false
        }) {
            viewModel.deleteNote()

        }
    }

    BaseScaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    MyTextView(
                        text = "Note",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    if (navigationType == NavType.BOTTOM_NAVIGATION) {
                        ActionButton(
                            onClick = { navController.popBackStack() },
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            modifier = Modifier.padding(6.dp),
                            contentDescription = "Back Icon"
                        )
                    }
                },
                actions = {
                    ActionButton(
                        onClick = {
                            if (note != null) {
                                navController.navigate(NavItem.EditNotes.route + "/${note.id.toString()}")
                            }
                        },
                        imageVector = Icons.Filled.Edit,
                        modifier = Modifier.padding(6.dp),
                        contentDescription = "Edit Icon"
                    )

                    ActionButton(
                        onClick = {
                            isDeleteDialog.value = true
                        },
                        imageVector = Icons.Filled.Delete,
                        modifier = Modifier.padding(6.dp),
                        contentDescription = "Delete Icon"
                    )
                },
            )
        },
        content = {
            if (note != null) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    LazyColumn(
                        content = {
                            item {
                                MyTextView(
                                    text = "Title",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            item {
                                NotesDetailCard(
                                    text = note.title,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            item {
                                MyTextView(
                                    text = "Notes",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            item {
                                NotesDetailCard(
                                    text = note.text,
                                    textStyle = MaterialTheme.typography.bodyLarge
                                )
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

            }
        }
    )

}