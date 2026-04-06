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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.arameu.R
import com.arameu.ui.theme.LocalSpacing

@Composable
fun CourseMapScreen(
    viewModel: CourseMapViewModel,
    onLessonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    val spacing = LocalSpacing.current

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

            // Auto-scroll to current lesson
            LaunchedEffect(s.currentLessonId) {
                if (s.currentLessonId != null) {
                    var index = 0
                    for (unit in s.units) {
                        index++ // unit header
                        for (lesson in unit.lessons) {
                            if (lesson.id == s.currentLessonId) {
                                listState.animateScrollToItem(index)
                                return@LaunchedEffect
                            }
                            index++
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
                item { Spacer(modifier = Modifier.height(spacing.sectionSpacing)) }

                for (unit in s.units) {
                    item(key = "unit-${unit.id}") {
                        UnitHeader(unit = unit)
                    }

                    items(
                        items = unit.lessons,
                        key = { "lesson-${it.id}" },
                    ) { lesson ->
                        val isCurrent = lesson.id == s.currentLessonId
                        LessonCard(
                            lesson = lesson,
                            isCurrent = isCurrent,
                            onClick = if (lesson.isUnlocked && !lesson.isCompleted) {
                                { onLessonClick(lesson.id) }
                            } else if (lesson.isCompleted) {
                                { onLessonClick(lesson.id) }
                            } else {
                                null
                            },
                        )
                    }

                    item { Spacer(modifier = Modifier.height(spacing.cardSpacing)) }
                }

                item { Spacer(modifier = Modifier.height(spacing.sectionSpacing)) }
            }
        }
    }
}

@Composable
private fun UnitHeader(unit: UnitWithProgress) {
    val spacing = LocalSpacing.current
    Text(
        text = unit.titleCa,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.sectionSpacing, bottom = spacing.elementSpacing),
    )
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
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.size(spacing.touchTarget),
                )
            }
        }
    }
}
