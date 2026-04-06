package com.arameu.ui.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arameu.R
import com.arameu.ui.theme.LocalSpacing

@Composable
fun CourseMapScreen(
    viewModel: CourseMapViewModel,
    onLessonClick: (Int) -> Unit,
    onShowWelcome: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    val spacing = LocalSpacing.current
    var showResetDialog by remember { mutableStateOf(false) }
    val expandedUnits = remember { mutableStateMapOf<Int, Boolean>() }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text(stringResource(R.string.course_restart)) },
            text = { Text(stringResource(R.string.course_restart_confirm)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetProgress()
                    showResetDialog = false
                }) { Text(stringResource(R.string.btn_confirm)) }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(stringResource(R.string.btn_cancel))
                }
            },
        )
    }

    when (val s = state) {
        is CourseMapUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is CourseMapUiState.Ready -> {
            val listState = rememberLazyListState()

            // Auto-expand the unit containing the current lesson
            LaunchedEffect(s.currentLessonId) {
                if (s.currentLessonId != null) {
                    for (unit in s.units) {
                        val hasCurrent = unit.lessons.any { it.id == s.currentLessonId }
                        if (hasCurrent) {
                            expandedUnits[unit.id] = true
                        }
                    }
                }
            }

            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing.contentPadding),
                verticalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
            ) {
                item {
                    Spacer(modifier = Modifier.height(spacing.elementSpacing))
                    TextButton(
                        onClick = onShowWelcome,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "\u0710  Arameu",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        )
                    }
                    Spacer(modifier = Modifier.height(spacing.elementSpacing))
                }

                for (unit in s.units) {
                    val completedCount = unit.lessons.count { it.isCompleted }
                    val totalCount = unit.lessons.size
                    val hasAnyUnlocked = unit.lessons.any { it.isUnlocked || it.isCompleted }
                    val isExpanded = expandedUnits[unit.id] ?: false

                    item(key = "unit-${unit.id}") {
                        UnitHeader(
                            unit = unit,
                            completedCount = completedCount,
                            totalCount = totalCount,
                            isExpanded = isExpanded,
                            isLocked = !hasAnyUnlocked,
                            onToggle = {
                                expandedUnits[unit.id] = !isExpanded
                            },
                        )
                    }

                    if (isExpanded) {
                        for (lesson in unit.lessons) {
                            item(key = "lesson-${lesson.id}") {
                                val isCurrent = lesson.id == s.currentLessonId
                                LessonCard(
                                    lesson = lesson,
                                    isCurrent = isCurrent,
                                    onClick = if (lesson.isUnlocked || lesson.isCompleted) {
                                        { onLessonClick(lesson.id) }
                                    } else {
                                        null
                                    },
                                )
                            }
                        }
                    }

                    item(key = "spacer-${unit.id}") { Spacer(modifier = Modifier.height(spacing.elementSpacing)) }
                }

                item {
                    Spacer(modifier = Modifier.height(spacing.sectionSpacing * 2))
                    TextButton(
                        onClick = { showResetDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(R.string.course_restart),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                        )
                    }
                    Spacer(modifier = Modifier.height(spacing.sectionSpacing))
                }
            }
        }
    }
}

@Composable
private fun UnitHeader(
    unit: UnitWithProgress,
    completedCount: Int,
    totalCount: Int,
    isExpanded: Boolean,
    isLocked: Boolean = false,
    onToggle: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val allDone = completedCount == totalCount
    val headerAlpha = if (isLocked) 0.4f else 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(headerAlpha)
            .clickable(onClick = onToggle),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                allDone -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                isLocked -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                else -> MaterialTheme.colorScheme.surface
            },
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 2.dp else 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.cardSpacing, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = unit.titleCa,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = if (isLocked) "\uD83D\uDD12 $totalCount lliçons" else "$completedCount / $totalCount",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
            if (!isLocked) {
                Text(
                    text = if (isExpanded) "\u25B2" else "\u25BC",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                )
            }
        }
    }
}

@Composable
private fun LessonCard(
    lesson: LessonWithProgress,
    isCurrent: Boolean,
    onClick: (() -> Unit)?,
) {
    val spacing = LocalSpacing.current
    val alpha = when {
        isCurrent -> 1f
        lesson.isCompleted -> 0.7f
        lesson.isUnlocked -> 0.85f
        else -> 0.3f
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isCurrent) 4.dp else 0.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            },
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.cardSpacing),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.titleCa,
                    style = if (isCurrent) {
                        MaterialTheme.typography.titleLarge
                    } else {
                        MaterialTheme.typography.titleMedium
                    },
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (isCurrent) {
                    Text(
                        text = stringResource(R.string.btn_continue),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            if (lesson.isCompleted) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "\u2713",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    )
                    lesson.score?.let { score ->
                        Text(
                            text = "$score%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        )
                    }
                }
            }
        }
    }
}
