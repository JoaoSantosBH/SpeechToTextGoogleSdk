package com.jomar.speechtotext.stp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomInstructions() {
    Text(
        text = "Toque no botão do microfone e fale. O texto será transcrito automaticamente.",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun BottomInstructionsPreview() {
    BottomInstructions()
}