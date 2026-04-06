package com.arameu.ui.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.arameu.R
import com.arameu.data.entity.Exercise
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.Terracotta

@Composable
fun ListenRepeatExercise(
    exercise: Exercise,
    onPlayAudio: (String) -> Unit,
    onAnswer: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(exercise.id) {
        exercise.promptAudioId?.let { onPlayAudio(it) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = exercise.promptScript ?: "",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(spacing.cardSpacing))

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

        Spacer(modifier = Modifier.height(spacing.sectionSpacing))

        androidx.compose.material3.TextButton(
            onClick = { exercise.promptAudioId?.let { onPlayAudio(it) } },
        ) {
            Text(
                text = stringResource(R.string.btn_replay),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.cardSpacing),
        ) {
            OutlinedButton(
                onClick = { exercise.promptAudioId?.let { onPlayAudio(it) } },
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.btn_repeat))
            }

            Button(
                onClick = { onAnswer(true) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
            ) {
                Text(stringResource(R.string.feedback_got_it))
            }
        }
    }
}
