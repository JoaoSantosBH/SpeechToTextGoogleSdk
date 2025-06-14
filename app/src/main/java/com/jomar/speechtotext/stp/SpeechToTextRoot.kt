package com.jomar.speechtotext.stp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.util.Locale

const val EMPTY_STRING = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeechToTextRoot() {
    val context = LocalContext.current
    var textFieldValue by remember { mutableStateOf(EMPTY_STRING) }
    var isListening by remember { mutableStateOf(false) }
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        if (!isGranted) {
            Toast.makeText(
                context,
                "Permissão de áudio necessária para reconhecimento de voz",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isListening = false
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            spokenText?.let { results ->
                if (results.isNotEmpty()) {
                    textFieldValue = results[0]
                }
            }
        }
    }

    fun startSpeechRecognition() {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            return
        }

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(
                context,
                "Reconhecimento de voz não disponível",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale agora...")
        }

        try {
            isListening = true
            speechRecognizerLauncher.launch(intent)
        } catch (e: Exception) {
            isListening = false
            Toast.makeText(
                context,
                "Erro ao iniciar reconhecimento de voz",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    SpeechToTextScreen(
        text = textFieldValue,
        isListening = isListening,
        onTextChange = { textFieldValue = it },
        onStartListening = { startSpeechRecognition() },
        onClearText = { textFieldValue = EMPTY_STRING }
    )
}



