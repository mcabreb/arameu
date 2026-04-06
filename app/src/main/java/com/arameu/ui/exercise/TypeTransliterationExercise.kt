package com.arameu.ui.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.arameu.R
import com.arameu.data.entity.Exercise
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.Terracotta
import com.arameu.util.AnswerValidator
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

@Composable
fun TypeTransliterationExercise(
    exercise: Exercise,
    onAnswer: (Boolean) -> Unit,
    onPlayAudio: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val acceptedVariants = remember(exercise.id) {
        exercise.acceptedVariants?.let {
            try { Json.decodeFromString<List<String>>(it) } catch (_: Exception) { null }
        }
    }

    var userInput by remember(exercise.id) { mutableStateOf("") }
    var showFeedback by remember(exercise.id) { mutableStateOf(false) }
    var isCorrect by remember(exercise.id) { mutableStateOf(false) }

    fun validate() {
        if (showFeedback) return
        isCorrect = AnswerValidator.validate(userInput, exercise.correctAnswer, acceptedVariants)
        showFeedback = true
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
        )

        exercise.promptScript?.let { script ->
            Text(
                text = script,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

        if (exercise.promptAudioId != null && onPlayAudio != null) {
            androidx.compose.material3.TextButton(
                onClick = { onPlayAudio(exercise.promptAudioId) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(R.string.btn_listen),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.elementSpacing))

        OutlinedTextField(
            value = userInput,
            onValueChange = { if (!showFeedback) userInput = it },
            modifier = Modifier.fillMaxWidth(),
            enabled = !showFeedback,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { validate() }),
        )

        Button(
            onClick = { validate() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !showFeedback && userInput.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
        ) {
            Text(stringResource(R.string.btn_check))
        }

        if (showFeedback) {
            Spacer(modifier = Modifier.height(spacing.elementSpacing))
            if (isCorrect) {
                Text(
                    text = "✓ ${exercise.correctAnswer}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Terracotta,
                )
            } else {
                Text(
                    text = stringResource(R.string.feedback_answer_is, exercise.correctAnswer),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            exercise.promptScript?.let { script ->
                Text(
                    text = script,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
            }
        }
    }
}
