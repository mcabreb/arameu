package com.arameu.ui.exercise

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateListOf
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentenceBuildExercise(
    exercise: Exercise,
    onAnswer: (Boolean) -> Unit,
    onPlayAudio: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    // Parse the correct word order from options (pipe-separated: "word1|word2|word3")
    // or from correctAnswer (space-separated)
    val correctWords = remember(exercise.id) {
        exercise.correctAnswer.split(" ").filter { it.isNotBlank() }
    }
    val shuffledWords = remember(exercise.id) {
        correctWords.shuffled()
    }

    val placed = remember { mutableStateListOf<String>() }
    val available = remember(exercise.id) {
        mutableStateListOf<String>().apply { addAll(shuffledWords) }
    }
    var showFeedback by remember(exercise.id) { mutableStateOf(false) }
    var isCorrect by remember(exercise.id) { mutableStateOf(false) }
    var hadMistake by remember(exercise.id) { mutableStateOf(false) }

    LaunchedEffect(showFeedback) {
        if (showFeedback && isCorrect) {
            exercise.promptAudioId?.let { onPlayAudio?.invoke(it) }
            delay(1500)
            onAnswer(!hadMistake)
        }
    }

    // Check if all words placed
    LaunchedEffect(placed.size) {
        if (placed.size == correctWords.size && !showFeedback) {
            val correct = placed.toList() == correctWords
            if (correct) {
                isCorrect = true
                showFeedback = true
            } else {
                // Wrong order — bounce back last word
                hadMistake = true
                delay(500)
                val last = placed.removeLastOrNull()
                if (last != null) available.add(last)
            }
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
        )

        Spacer(modifier = Modifier.height(spacing.elementSpacing))

        // Answer area — placed words
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (showFeedback && isCorrect) {
                    Terracotta.copy(alpha = 0.1f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                },
            ),
        ) {
            FlowRow(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                placed.forEach { word ->
                    Text(
                        text = word,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.cardSpacing))

        // Available word cards
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
            verticalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
        ) {
            available.forEach { word ->
                Card(
                    modifier = Modifier.clickable(enabled = !showFeedback) {
                        placed.add(word)
                        available.remove(word)
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Text(
                        text = word,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Reset button
        if (placed.isNotEmpty() && !showFeedback) {
            TextButton(
                onClick = {
                    available.addAll(placed)
                    placed.clear()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(R.string.feedback_try_again),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        if (showFeedback && isCorrect) {
            Text(
                text = correctWords.joinToString(" "),
                style = MaterialTheme.typography.titleMedium,
                color = Terracotta,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}
