package com.example.myapplication.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.airbnb.lottie.compose.*
import com.example.myapplication.R
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.presentation.theme.MyApplicationTheme

enum class TimerStyle {
    CLASSIC,  // Original UI
    SAND_ANIMATION  // New sand animation UI
}

@Composable
fun StopwatchTimer(
    timerStyle: TimerStyle = TimerStyle.SAND_ANIMATION
) {
    when (timerStyle) {
        TimerStyle.CLASSIC -> ClassicTimer()
        TimerStyle.SAND_ANIMATION -> SandAnimationTimer()
    }
}

@Composable
private fun ClassicTimer() {
    // Lottie animation setup
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.sand_timer_res)
    )
    
    // Simple continuous animation
    val animationState by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        restartOnPlay = true,
        iterations = LottieConstants.IterateForever,
        // Slow down the animation
        speed = 0.3f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { animationState },
                modifier = Modifier.size(160.dp)
            )
        }
    }
}

@Composable
private fun SandAnimationTimer() {
    var timeInSeconds by remember { mutableStateOf(0) }
    var initialTime by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var isTimerScreen by remember { mutableStateOf(true) }
    var clickCount by remember { mutableStateOf(0) }
    
    // Lottie animation setup
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.sand_anim)
    )
    
    // Calculate animation progress based on remaining time
    val progress by remember(timeInSeconds, initialTime, isRunning) {
        derivedStateOf {
            if (!isRunning) 0.11f
            else {
                // Map timeInSeconds from [initialTime..0] to [0.1..0.52]
                val progressRange = 0.50f - 0.11f
                val timeProgress = 1f - (timeInSeconds.toFloat() / initialTime.toFloat())
                0.11f + (progressRange * timeProgress)
            }
        }
    }
    
    // Control animation progress manually insteadm of auto-playing
    val animationState by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        restartOnPlay = false,
        iterations = 1,
        speed = 0f
    )

    LaunchedEffect(isRunning) {
        while (isRunning && timeInSeconds > 0) {
            delay(1000)
            timeInSeconds--
            if (timeInSeconds == 0) {
                isRunning = false
                isTimerScreen = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isTimerScreen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Timer Selection Screen
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formatTime(timeInSeconds),
                    color = Color.White,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { if (timeInSeconds > 0) {
                            if (timeInSeconds - 60 < 0) timeInSeconds = 0
                            else timeInSeconds -= 60
                        } },
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEB0528))
                    ) {
                        Text("-", fontSize = 20.sp)
                    }

                    Button(
                        onClick = { if (timeInSeconds < 3600) timeInSeconds += 60 },
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
                    ) {
                        Text("+", fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { 
                        if (timeInSeconds > 0) {
                            initialTime = timeInSeconds  // Store initial time when starting
                            isRunning = true
                            isTimerScreen = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    shape = CircleShape,
                    modifier = Modifier.size(60.dp)
                ) {
                    Text("Start", fontSize = 16.sp)
                }
            }
        }

        AnimatedVisibility(
            visible = !isTimerScreen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Running Timer Screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        clickCount++
                        if (clickCount >= 2) {
                            isRunning = false
                            isTimerScreen = true
                            clickCount = 0
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.size(120.dp)
                    )
                    
                    Text(
                        text = formatTime(timeInSeconds),
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Preview(name = "Classic Timer")
@Composable
private fun PreviewClassicTimer() {
    MyApplicationTheme {
        StopwatchTimer(TimerStyle.CLASSIC)
    }
}

@Preview(name = "Sand Animation Timer")
@Composable
private fun PreviewSandTimer() {
    MyApplicationTheme {
        StopwatchTimer(TimerStyle.SAND_ANIMATION)
    }
}