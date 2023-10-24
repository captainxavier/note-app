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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xavier.noteapp.nav.NavItem
import com.xavier.noteapp.nav.NavType
import com.xavier.noteapp.ui.presentation.components.buttons.ActionButton
import com.xavier.noteapp.ui.presentation.components.dialog.LoadingDialog
import com.xavier.noteapp.ui.presentation.components.scaffold.BaseScaffold
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.presentation.notes.events.SaveNoteFormEvent
import com.xavier.noteapp.ui.presentation.notes.view_models.NotesViewModel
import com.xavier.noteapp.ui.theme.Grey50
import com.xavier.noteapp.ui.theme.RED15
import com.xavier.noteapp.ui.theme.Thistle35
import com.xavier.noteapp.ui.theme.Thistle65

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateModeScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    navigationType: NavType
) {

    val TAG = "CreateModeScreen"

    val context = LocalContext.current
    val isLoading = remember {
        mutableStateOf(false)
    }

    val showSaveButton = remember { mutableStateOf(false) }
    val state = viewModel.state

    LaunchedEffect(key1 = viewModel.stateSaveNotes) {
        viewModel.stateSaveNotes.collect {
            isLoading.value = it.isLoading
            if (!it.message.isNullOrBlank()) {
                Log.d(TAG, "CreateModeScreen: ${it.message}")
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                navController.navigate(NavItem.Home.route)
            }
            if (it.errorMessage.isNotEmpty()) {
                Log.e(TAG, "CreateModeScreen: ${it.errorMessage}")
            }
        }
    }

    if (isLoading.value) {
        LoadingDialog()
    }

    BaseScaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    MyTextView(
                        text = "Create Note",
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
                            onClick = { viewModel.onCreateNotesEvent(SaveNoteFormEvent.SubmitNotes) },
                            imageVector = Icons.Filled.Save,
                            modifier = Modifier.padding(6.dp),
                            contentDescription = "Save Icon"
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
                viewModel = viewModel
            )
        }
    )
}