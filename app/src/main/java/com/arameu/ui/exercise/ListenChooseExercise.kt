package com.arameu.ui.exercise

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arameu.R
import com.arameu.data.entity.Exercise
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.Terracotta
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

@Composable
fun ListenChooseExercise(
    exercise: Exercise,
    onAnswer: (Boolean) -> Unit,
    onPlayAudio: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val options = remember(exercise.id) {
        exercise.options?.let {
            try { Json.decodeFromString<List<String>>(it).shuffled() } catch (_: Exception) { emptyList() }
        } ?: emptyList()
    }

    var selectedOption by remember(exercise.id) { mutableStateOf<String?>(null) }
    var showFeedback by remember(exercise.id) { mutableStateOf(false) }
    var isCorrect by remember(exercise.id) { mutableStateOf(false) }

    // Auto-play audio on load
    LaunchedEffect(exercise.id) {
        exercise.promptAudioId?.let { onPlayAudio(it) }
    }

    LaunchedEffect(showFeedback) {
        if (showFeedback) {
            delay(1500)
            onAnswer(isCorrect)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.contentPadding),
        verticalArrangement = Arrangement.spacedBy(spacing.cardSpacing),
    ) {
        Text(
            text = exercise.promptText,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = spacing.elementSpacing),
        )

        TextButton(
            onClick = { exercise.promptAudioId?.let { onPlayAudio(it) } },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = stringResource(R.string.btn_replay),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        options.forEach { option ->
            val isSelected = selectedOption == option
            val isCorrectOption = option == exercise.correctAnswer

            val bgColor by animateColorAsState(
                targetValue = when {
                    !showFeedback -> MaterialTheme.colorScheme.surface
                    isSelected && isCorrect -> Terracotta.copy(alpha = 0.15f)
                    isSelected && !isCorrect -> MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                    isCorrectOption && showFeedback -> Terracotta.copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surface
                },
                animationSpec = tween(400),
                label = "option_bg",
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (!showFeedback) Modifier.clickable {
                            selectedOption = option
                            isCorrect = option == exercise.correctAnswer
                            showFeedback = true
                        } else Modifier
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(spacing.cardSpacing),
                )
            }
        }
    }
}
