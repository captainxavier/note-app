package com.xavier.noteapp.ui.presentation.notes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xavier.noteapp.ui.presentation.components.card.NotesCard
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.presentation.notes.view_models.NotesViewModel
import com.xavier.noteapp.ui.theme.LightGreen
import com.xavier.noteapp.ui.theme.LightSalmonPink
import com.xavier.noteapp.ui.theme.PaleViolet
import com.xavier.noteapp.ui.theme.PastelYellow
import com.xavier.noteapp.ui.theme.RichBrilliantLavender
import com.xavier.noteapp.ui.theme.Waterspout
import com.xavier.noteapp.utils.colorToLong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNotesScreen(
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier,
    onNoteClicked: (String) -> Unit
) {
    val query = remember { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    val notes = viewModel.stateGetNotes.collectAsStateWithLifecycle().value.notes

    if (notes != null) {
        if (notes.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(),
                content = {
                    MyTextView(
                        text = "No notes available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                })
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = query.value,
                    onQueryChange = {
                        query.value = it
                        viewModel.searchNotes(query = it)
                    },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = {
                        MyTextView(
                            text = "Search notes",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    trailingIcon = {
                        if (active) {
                            IconButton(onClick = { active = false }) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Cancel Icon close",
                                    tint = MaterialTheme.colorScheme.onSecondary
                                )

                            }
                        }
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        dividerColor = MaterialTheme.colorScheme.onSecondary,
                        inputFieldColors = TextFieldDefaults.colors()
                    )
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(notes) { index, item ->
                            val colorList: List<Color> =
                                listOf(
                                    RichBrilliantLavender,
                                    LightSalmonPink,
                                    LightGreen,
                                    PastelYellow,
                                    Waterspout,
                                    PaleViolet
                                )
                            val color = colorList[index % colorList.size]
                            NotesCard(
                                note = item,
                                color = colorToLong(color),
                                onClick = {
                                    onNoteClicked(item.id.toString())
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                LazyColumn(
                    content = {
                        itemsIndexed(notes) { index, item ->
                            val colorList: List<Color> =
                                listOf(
                                    RichBrilliantLavender,
                                    LightSalmonPink,
                                    LightGreen,
                                    PastelYellow,
                                    Waterspout,
                                    PaleViolet
                                )
                            val color = colorList[index % colorList.size]
                            NotesCard(
                                note = item,
                                color = colorToLong(color),
                                onClick = {
                                    onNoteClicked(item.id.toString())

//                                    navController.navigate(NavItem.CreateNotes.route + "/${id}")
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    },
                    contentPadding = PaddingValues(bottom = 8.dp)
                )
            }
        }
    }
}