package com.arameu.ui.lesson

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arameu.R
import com.arameu.ui.exercise.IntroScreen
import com.arameu.ui.exercise.ListenChooseExercise
import com.arameu.ui.exercise.ListenRepeatExercise
import com.arameu.ui.exercise.MatchingExercise
import com.arameu.ui.exercise.MultipleChoiceExercise
import com.arameu.ui.exercise.ScriptWriteExercise
import com.arameu.ui.exercise.SentenceBuildExercise
import com.arameu.ui.exercise.SummaryScreen
import com.arameu.ui.exercise.TypeTransliterationExercise
import com.arameu.ui.theme.LocalSpacing

@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onFinished: () -> Unit,
    onBack: () -> Unit = onFinished,
    modifier: Modifier = Modifier,
    onPlayAudio: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    val spacing = LocalSpacing.current

    when (val s = state) {
        is LessonUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is LessonUiState.Active -> {
            Column(modifier = modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.contentPadding, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "\u2190 ${stringResource(R.string.btn_back)}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable(onClick = onBack)
                            .padding(8.dp),
                    )
                }

                LinearProgressIndicator(
                    progress = { (s.exerciseIndex.toFloat() / s.totalExercises).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.contentPadding),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )

                Spacer(modifier = Modifier.height(spacing.cardSpacing))

                AnimatedContent(
                    targetState = s.exerciseIndex,
                    transitionSpec = { fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) togetherWith fadeOut(animationSpec = androidx.compose.animation.core.tween(300)) },
                    label = "exercise_transition",
                ) { _ ->
                    val exercise = s.currentExercise
                    if (s.phase == "intro" && exercise.promptScript != null) {
                        IntroScreen(
                            exercise = exercise,
                            onPlayAudio = onPlayAudio,
                            onContinue = { viewModel.onAnswer(true) },
                        )
                    } else when (exercise.type) {
                        "multiple_choice" -> MultipleChoiceExercise(
                            exercise = exercise,
                            onAnswer = { isCorrect -> viewModel.onAnswer(isCorrect) },
                            onPlayAudio = onPlayAudio,
                        )
                        "matching" -> MatchingExercise(
                            exercise = exercise,
                            onComplete = { correctOnFirstAttempt ->
                                viewModel.onAnswer(correctOnFirstAttempt)
                            },
                        )
                        "type_transliteration" -> TypeTransliterationExercise(
                            exercise = exercise,
                            onAnswer = { isCorrect -> viewModel.onAnswer(isCorrect) },
                            onPlayAudio = onPlayAudio,
                        )
                        "listen_repeat" -> ListenRepeatExercise(
                            exercise = exercise,
                            onPlayAudio = onPlayAudio,
                            onAnswer = { viewModel.onAnswer(true) },
                        )
                        "listen_choose" -> ListenChooseExercise(
                            exercise = exercise,
                            onAnswer = { isCorrect -> viewModel.onAnswer(isCorrect) },
                            onPlayAudio = onPlayAudio,
                        )
                        "script_write" -> ScriptWriteExercise(
                            exercise = exercise,
                            onAnswer = { isCorrect -> viewModel.onAnswer(isCorrect) },
                            onPlayAudio = onPlayAudio,
                        )
                        "sentence_build" -> SentenceBuildExercise(
                            exercise = exercise,
                            onAnswer = { isCorrect -> viewModel.onAnswer(isCorrect) },
                            onPlayAudio = onPlayAudio,
                        )
                        else -> MultipleChoiceExercise(
                            exercise = exercise,
                            onAnswer = { isCorrect -> viewModel.onAnswer(isCorrect) },
                        )
                    }
                }
            }
        }

        is LessonUiState.Complete -> {
            SummaryScreen(
                score = s.score,
                introExercises = s.introExercises,
                onContinue = onFinished,
            )
        }
    }
}
