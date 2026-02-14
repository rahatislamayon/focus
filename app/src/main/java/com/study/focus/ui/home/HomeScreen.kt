package com.study.focus.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.focus.ui.FocusViewModel
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HomeScreen(
    viewModel: FocusViewModel,
    onStartSession: () -> Unit,
    onOpenDashboard: () -> Unit
) {
    val duration by viewModel.targetDurationMinutes.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ready to focus?",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(48.dp))

        CircularTimerSelector(
            durationMinutes = duration,
            onDurationChange = { viewModel.setDuration(it) }
        )
        
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                viewModel.startSession()
                onStartSession()
            },
            modifier = Modifier.size(width = 200.dp, height = 56.dp)
        ) {
            Text("Start Focus")
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onOpenDashboard) {
            Text("View Analytics")
        }
    }
}

@Composable
fun CircularTimerSelector(
    durationMinutes: Int,
    onDurationChange: (Int) -> Unit
) {
    val maxDuration = 120 // 2 hours for dial
    var angle by remember { mutableFloatStateOf(0f) }

    // Sync initial angle
    LaunchedEffect(durationMinutes) {
        angle = (durationMinutes.toFloat() / maxDuration) * 360f
    }

    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(250.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val touchPoint = change.position
                        val dx = touchPoint.x - center.x
                        val dy = touchPoint.y - center.y
                        var theta = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat() + 90f
                        if (theta < 0) theta += 360f
                        
                        // Round value to nearest 5
                        val rawMinutes = (theta / 360f * maxDuration).toInt()
                        val steppedMinutes = (rawMinutes / 5) * 5
                        val clampedMinutes = steppedMinutes.coerceIn(5, maxDuration)
                        
                        onDurationChange(clampedMinutes)
                    }
                }
        ) {
            val radius = size.minDimension / 2
            
            // Track background
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.3f),
                style = Stroke(width = 40f)
            )

            // Progress arc
            drawArc(
                color = Color(0xFF6200EE), // TODO: Use theme color
                startAngle = -90f,
                sweepAngle = (durationMinutes.toFloat() / maxDuration) * 360f,
                useCenter = false,
                style = Stroke(width = 40f, cap = StrokeCap.Round)
            )
            
            // Indicator Knob
            val knobAngle = (durationMinutes.toFloat() / maxDuration) * 360f - 90f
            val knobRad = Math.toRadians(knobAngle.toDouble())
            val knobX = (center.x + (radius * cos(knobRad))).toFloat()
            val knobY = (center.y + (radius * sin(knobRad))).toFloat()
            
            drawCircle(
                color = Color.White,
                radius = 15f,
                center = Offset(knobX, knobY)
            )
        }
        
        Text(
            text = "$durationMinutes\nmin",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
