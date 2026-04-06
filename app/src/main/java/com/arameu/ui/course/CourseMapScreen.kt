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
import com.arameu.ui.theme.LighterParchment
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.MutedGold
import com.arameu.ui.theme.Parchment
import com.arameu.ui.theme.Terracotta

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
                        if (unit.lessons.any { it.id == s.currentLessonId }) {
                            expandedUnits[unit.id] = true
                        }
                    }
                }
            }

            // Determine which unit is the "active" one (contains current lesson)
            val activeUnitId = s.units.firstOrNull { unit ->
                unit.lessons.any { it.id == s.currentLessonId }
            }?.id

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
                            color = Terracotta.copy(alpha = 0.6f),
                        )
                    }
                    Spacer(modifier = Modifier.height(spacing.elementSpacing))
                }

                for (unit in s.units) {
                    val completedCount = unit.lessons.count { it.isCompleted }
                    val totalCount = unit.lessons.size
                    val hasAnyUnlocked = unit.lessons.any { it.isUnlocked || it.isCompleted }
                    val allDone = completedCount == totalCount && totalCount > 0
                    val isActive = unit.id == activeUnitId
                    val isLocked = !hasAnyUnlocked
                    val isExpanded = expandedUnits[unit.id] ?: false

                    // Unit header
                    item(key = "unit-${unit.id}") {
                        val headerState = when {
                            allDone -> UnitState.COMPLETED
                            isActive -> UnitState.ACTIVE
                            isLocked -> UnitState.LOCKED
                            else -> UnitState.AVAILABLE
                        }
                        UnitHeader(
                            titleCa = unit.titleCa,
                            completedCount = completedCount,
                            totalCount = totalCount,
                            isExpanded = isExpanded,
                            unitState = headerState,
                            onToggle = { expandedUnits[unit.id] = !isExpanded },
                        )
                    }

                    // Lessons (when expanded)
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

                    item(key = "spacer-${unit.id}") {
                        Spacer(modifier = Modifier.height(spacing.elementSpacing))
                    }
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

// --- Unit header states ---

private enum class UnitState { COMPLETED, ACTIVE, AVAILABLE, LOCKED }

@Composable
private fun UnitHeader(
    titleCa: String,
    completedCount: Int,
    totalCount: Int,
    isExpanded: Boolean,
    unitState: UnitState,
    onToggle: () -> Unit,
) {
    val spacing = LocalSpacing.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (unitState == UnitState.LOCKED) 0.45f else 1f)
            .clickable(onClick = onToggle),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (unitState) {
                UnitState.COMPLETED -> MutedGold.copy(alpha = 0.18f)
                UnitState.ACTIVE -> Terracotta.copy(alpha = 0.15f)
                UnitState.AVAILABLE -> LighterParchment.copy(alpha = 0.6f)
                UnitState.LOCKED -> Parchment
            },
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = when (unitState) {
                UnitState.ACTIVE -> 3.dp
                UnitState.COMPLETED -> 0.dp
                else -> 1.dp
            },
        ),
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
                    text = titleCa,
                    style = MaterialTheme.typography.titleLarge,
                    color = when (unitState) {
                        UnitState.ACTIVE -> Terracotta
                        UnitState.COMPLETED -> MutedGold.copy(alpha = 0.8f)
                        else -> MaterialTheme.colorScheme.onBackground
                    },
                )
                Text(
                    text = when (unitState) {
                        UnitState.COMPLETED -> "\u2713 $totalCount lliçons"
                        UnitState.ACTIVE -> "$completedCount / $totalCount lliçons"
                        UnitState.AVAILABLE -> "$completedCount / $totalCount lliçons"
                        UnitState.LOCKED -> "$totalCount lliçons"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = when (unitState) {
                        UnitState.COMPLETED -> MutedGold.copy(alpha = 0.6f)
                        UnitState.ACTIVE -> Terracotta.copy(alpha = 0.6f)
                        else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                    },
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
            Text(
                text = if (isExpanded) "\u25B2" else "\u25BC",
                style = MaterialTheme.typography.bodyMedium,
                color = when (unitState) {
                    UnitState.ACTIVE -> Terracotta.copy(alpha = 0.5f)
                    UnitState.COMPLETED -> MutedGold.copy(alpha = 0.5f)
                    else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                },
            )
        }
    }
}

// --- Lesson card ---

@Composable
private fun LessonCard(
    lesson: LessonWithProgress,
    isCurrent: Boolean,
    onClick: (() -> Unit)?,
) {
    val spacing = LocalSpacing.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(
                when {
                    isCurrent -> 1f
                    lesson.isCompleted -> 0.75f
                    lesson.isUnlocked -> 0.6f
                    else -> 0.25f
                }
            )
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isCurrent) 4.dp else 0.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCurrent -> Terracotta.copy(alpha = 0.12f)
                lesson.isCompleted -> MutedGold.copy(alpha = 0.08f)
                else -> MaterialTheme.colorScheme.surface
            },
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.cardSpacing, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.titleCa,
                    style = if (isCurrent) {
                        MaterialTheme.typography.titleMedium
                    } else {
                        MaterialTheme.typography.bodyLarge
                    },
                    color = when {
                        isCurrent -> Terracotta
                        lesson.isCompleted -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                )
                if (isCurrent) {
                    Text(
                        text = stringResource(R.string.btn_continue),
                        style = MaterialTheme.typography.labelLarge,
                        color = Terracotta.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
            }
            if (lesson.isCompleted) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "\u2713",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedGold.copy(alpha = 0.7f),
                    )
                    lesson.score?.let { score ->
                        Text(
                            text = "$score%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MutedGold.copy(alpha = 0.4f),
                        )
                    }
                }
            }
        }
    }
}
