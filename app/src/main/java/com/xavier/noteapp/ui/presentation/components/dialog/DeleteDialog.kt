package com.xavier.noteapp.ui.presentation.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.theme.RED15

@Composable
fun DeleteDialog(openDialog: (Boolean) -> Unit, onDeleteClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { openDialog(false) },
        icon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info",
                tint = RED15
            )
        },
        title = {
            MyTextView(
                text = "Delete Alert",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onError
            )
        },
        text = {
            MyTextView(
                text = "Are you sure you want to delete the note?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog(false)
                    onDeleteClick()
                }
            ) {
                Text(
                    "Confirm",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog(false)
                }
            ) {
                Text(
                    "Dismiss",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    )
}