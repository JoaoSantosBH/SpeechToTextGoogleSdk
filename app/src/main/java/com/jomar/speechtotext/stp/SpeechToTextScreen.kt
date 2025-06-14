package com.jomar.speechtotext.stp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jomar.speechtotext.stp.components.BottomInstructions
import com.jomar.speechtotext.stp.components.ClearTextButton
import com.jomar.speechtotext.stp.components.PageTitle
import com.jomar.speechtotext.stp.components.SpeechButton
import com.jomar.speechtotext.stp.components.SpeechTextField

@Composable
fun SpeechToTextScreen(
    text: String,
    isListening: Boolean,
    onTextChange: (String) -> Unit,
    onStartListening: () -> Unit,
    onClearText: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PageTitle()
            SpeechTextField(text, onTextChange)
            Spacer(modifier = Modifier.height(24.dp))
            SpeechButton(onStartListening, isListening)
            Spacer(modifier = Modifier.height(16.dp))
            ClearTextButton(onClearText)
            Spacer(modifier = Modifier.height(24.dp))
            BottomInstructions()
        }
    }
}


