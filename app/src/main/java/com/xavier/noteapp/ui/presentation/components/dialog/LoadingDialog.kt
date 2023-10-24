package com.xavier.noteapp.ui.presentation.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.xavier.noteapp.R
import com.xavier.noteapp.ui.presentation.components.animations.MyLottieFileAnimation
import com.xavier.noteapp.ui.presentation.components.text.MyTextView
import com.xavier.noteapp.ui.theme.Thistle35
import com.xavier.noteapp.ui.theme.Thistle81
import com.xavier.noteapp.ui.theme.White85


@Composable
fun LoadingDialog(message: String = "Loading") {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.Transparent
        ) {
            val verticalSpace: Dp = 56.dp
            val horizontalSpace: Dp = 32.dp

            Column(
                modifier = Modifier
                    .padding(vertical = verticalSpace, horizontal = horizontalSpace),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                MyLottieFileAnimation(
//                    resId = R.raw.animation_loading,
//                    modifier = Modifier.size(180.dp)
//                )
                CircularProgressIndicator(
                    color = Thistle81,
                    trackColor = Thistle35,
                    modifier = Modifier.size(65.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextView(
                    modifier = Modifier
                        .padding(bottom = horizontalSpace)
                        .fillMaxWidth(),
                    text = message,
                    style = typography.bodyMedium,
                    color = White85,
                    textAlign = TextAlign.Center,
                )

            }
        }

    }

}