package com.xavier.noteapp.ui.presentation.notes.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xavier.noteapp.nav.NavItem
import com.xavier.noteapp.nav.NavType
import com.xavier.noteapp.ui.presentation.components.buttons.ActionButton
import com.xavier.noteapp.ui.presentation.components.dialog.DeleteDialog
import com.xavier.noteapp.ui.presentation.components.dialog.LoadingDialog
import com.xavier.noteapp.ui.presentation.components.scaffold.BaseScaffold
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.presentation.notes.events.SaveNoteFormEvent
import com.xavier.noteapp.ui.presentation.notes.states.SaveNoteFormState
import com.xavier.noteapp.ui.presentation.notes.view_models.NotesViewModel
import com.xavier.noteapp.ui.theme.Grey50
import com.xavier.noteapp.ui.theme.RED15
import com.xavier.noteapp.ui.theme.Thistle35
import com.xavier.noteapp.ui.theme.Thistle65

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditModeScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    navigationType: NavType
) {

    val TAG = "EditModeScreen"

    val context = LocalContext.current

    val showSaveButton = remember { mutableStateOf(false) }
    val showDeleteButton = remember { mutableStateOf(false) }

    val isLoading = remember {
        mutableStateOf(false)
    }

    val isDeleteDialog = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getNotesByIdEditMode()
    }

    val state = viewModel.state

    LaunchedEffect(key1 = viewModel.stateSaveNotes) {
        viewModel.stateSaveNotes.collect {
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
                        text = "Edit Note",
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
                    if (showSaveButton.value) {
                        ActionButton(
                            onClick = {
                                viewModel.onCreateNotesEvent(SaveNoteFormEvent.SubmitNotes)
                            },
                            imageVector = Icons.Filled.Save,
                            modifier = Modifier.padding(6.dp),
                            contentDescription = "Save Icon"
                        )
                    }

                    if (showDeleteButton.value) {
                        showSaveButton.value = false
                        ActionButton(
                            onClick = {
                                isDeleteDialog.value = true
                            },
                            imageVector = Icons.Filled.Delete,
                            modifier = Modifier.padding(6.dp),
                            contentDescription = "Delete Icon"
                        )
                    }
                }
            )
        },
        content = {
            EditModeForm(
                modifier = Modifier.padding(it),
                state = state,
                showSaveButton = { result ->
                    showSaveButton.value = result
                },
                viewModel = viewModel,
                showDeleteButton = { result ->
                    showDeleteButton.value = result
                },
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditModeForm(
    viewModel: NotesViewModel,
    state: SaveNoteFormState,
    modifier: Modifier = Modifier,
    showSaveButton: (Boolean) -> Unit = {},
    showDeleteButton: (Boolean) -> Unit = {}
) {

    val (focusTitle, focusNotes) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusTitle.requestFocus()
    }

    LazyColumn(
        content = {
            item {
                MyTextView(
                    text = "Title",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusTitle),
                    value = state.title,
                    onValueChange = {
                        viewModel.onCreateNotesEvent(
                            SaveNoteFormEvent.TitleChanged(
                                it
                            )
                        )
                        showSaveButton(true)
                    },
                    placeholder = {
                        MyTextView(
                            text = "Enter Notes",
                            style = MaterialTheme.typography.labelLarge,
                            color = Grey50
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Thistle35,
                        errorCursorColor = RED15,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Thistle65,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onNext = { focusNotes.requestFocus() })
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                MyTextView(
                    text = "Notes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusNotes),
                    value = state.notes,
                    onValueChange = {
                        viewModel.onCreateNotesEvent(SaveNoteFormEvent.NotesChanged(it))
                        showSaveButton(true)
                        showDeleteButton(it.isEmpty())
                    },
                    placeholder = {
                        MyTextView(
                            text = "Enter Notes",
                            style = MaterialTheme.typography.labelLarge,
                            color = Grey50
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Thistle35,
                        errorCursorColor = RED15,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Thistle65,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )
            }
        }, modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp, horizontal = 16.dp)
    )
}
