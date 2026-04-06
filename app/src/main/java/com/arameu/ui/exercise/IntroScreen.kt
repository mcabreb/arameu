package com.arameu.ui.exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.arameu.R
import com.arameu.data.entity.Exercise
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.Terracotta
import kotlinx.coroutines.delay

@Composable
fun IntroScreen(
    exercise: Exercise,
    onPlayAudio: (String) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    var showScript by remember(exercise.id) { mutableStateOf(false) }
    var showDetails by remember(exercise.id) { mutableStateOf(false) }
    var showButton by remember(exercise.id) { mutableStateOf(false) }

    LaunchedEffect(exercise.id) {
        exercise.promptAudioId?.let { onPlayAudio(it) }
        delay(1500)
        showScript = true
        delay(1000)
        showDetails = true
        delay(500)
        showButton = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            visible = showScript,
            enter = fadeIn(tween(600)),
        ) {
            Text(
                text = exercise.promptScript ?: "",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.height(spacing.cardSpacing))

        AnimatedVisibility(
            visible = showDetails,
            enter = fadeIn(tween(600)),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = exercise.correctAnswer,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = exercise.promptText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.sectionSpacing))

        if (showDetails) {
            IconButton(
                onClick = { exercise.promptAudioId?.let { onPlayAudio(it) } },
            ) {
                Text("🔊", style = MaterialTheme.typography.headlineMedium)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(tween(400)),
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
            ) {
                Text(stringResource(R.string.btn_continue))
            }
        }
    }
}
