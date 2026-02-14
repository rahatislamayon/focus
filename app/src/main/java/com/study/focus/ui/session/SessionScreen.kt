package com.study.focus.ui.session

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.study.focus.ui.FocusViewModel
import java.util.Locale

@Composable
fun SessionScreen(
    viewModel: FocusViewModel,
    onSessionComplete: () -> Unit
) {
    val remainingSeconds by viewModel.remainingTimeSeconds.collectAsState()
    val isSessionActive by viewModel.isSessionActive.collectAsState()

    LaunchedEffect(isSessionActive) {
        if (!isSessionActive && remainingSeconds == 0L) {
            onSessionComplete()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(remainingSeconds),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Stay Focused",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(64.dp))
        
        // Todo: Make this harder to click or require validation
        Button(
            onClick = { viewModel.stopSession() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Give Up")
        }
    }
}

fun formatTime(seconds: Long): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", mins, secs)
}
