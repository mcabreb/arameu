package com.arameu.ui.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arameu.data.entity.Exercise
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.Terracotta

@Composable
fun SummaryScreen(
    score: Int,
    introExercises: List<Exercise>,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.contentPadding),
        verticalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
    ) {
        Text(
            text = "Has après:",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(spacing.cardSpacing))

        introExercises.forEach { exercise ->
            Column(
                modifier = Modifier.padding(bottom = spacing.cardSpacing),
            ) {
                exercise.promptScript?.let { script ->
                    Text(
                        text = script,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Text(
                    text = exercise.correctAnswer,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                )
                Text(
                    text = exercise.promptText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
        ) {
            Text(
                text = "Continua",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}
