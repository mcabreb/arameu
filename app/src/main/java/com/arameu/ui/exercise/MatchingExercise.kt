package com.arameu.ui.exercise

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.arameu.data.entity.Exercise
import com.arameu.ui.theme.LocalSpacing
import com.arameu.ui.theme.Terracotta
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

data class MatchPair(val left: String, val right: String, val index: Int)

@Composable
fun MatchingExercise(
    exercise: Exercise,
    onComplete: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val pairs = remember(exercise.id) {
        exercise.options?.let { optionsJson ->
            try {
                Json.decodeFromString<List<String>>(optionsJson).mapIndexed { idx, pair ->
                    val parts = pair.split("|", limit = 2)
                    if (parts.size == 2) MatchPair(parts[0], parts[1], idx) else null
                }.filterNotNull()
            } catch (_: Exception) { emptyList() }
        } ?: emptyList()
    }

    val leftItems = remember(exercise.id) { pairs.map { it.left }.shuffled() }
    val rightItems = remember(exercise.id) { pairs.map { it.right }.shuffled() }
    val matched = remember { mutableStateListOf<Int>() }
    val mistakenPairs = remember { mutableStateListOf<Int>() }
    var selectedLeft by remember { mutableStateOf<String?>(null) }
    var selectedRight by remember { mutableStateOf<String?>(null) }
    var incorrectFlash by remember { mutableStateOf(false) }

    // Check match when both selected
    LaunchedEffect(selectedLeft, selectedRight) {
        val l = selectedLeft
        val r = selectedRight
        if (l != null && r != null) {
            val isMatch = pairs.any { it.left == l && it.right == r }
            if (isMatch) {
                val pairIdx = pairs.indexOfFirst { it.left == l }
                matched.add(pairIdx)
                selectedLeft = null
                selectedRight = null
                if (matched.size == pairs.size) {
                    delay(500)
                    val correctOnFirst = pairs.size - mistakenPairs.distinct().size
                    onComplete(correctOnFirst == pairs.size)
                }
            } else {
                // Track which pairs had mistakes (by left item attempted)
                val attemptedPairIdx = pairs.indexOfFirst { it.left == l }
                if (attemptedPairIdx >= 0) mistakenPairs.add(attemptedPairIdx)
                incorrectFlash = true
                delay(500)
                incorrectFlash = false
                selectedLeft = null
                selectedRight = null
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.contentPadding),
        verticalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
    ) {
        Text(
            text = exercise.promptText,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = spacing.elementSpacing),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
            ) {
                leftItems.forEachIndexed { _, item ->
                    val pairIdx = pairs.indexOfFirst { it.left == item }
                    val isMatched = matched.contains(pairIdx)
                    val isSelected = selectedLeft == item

                    MatchItem(
                        text = item,
                        isSelected = isSelected,
                        isMatched = isMatched,
                        onClick = if (!isMatched) {{ selectedLeft = item }} else null,
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.elementSpacing),
            ) {
                rightItems.forEachIndexed { _, item ->
                    val pairIdx = pairs.indexOfFirst { it.right == item }
                    val isMatched = matched.contains(pairIdx)
                    val isSelected = selectedRight == item

                    MatchItem(
                        text = item,
                        isSelected = isSelected,
                        isMatched = isMatched,
                        onClick = if (!isMatched) {{ selectedRight = item }} else null,
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchItem(
    text: String,
    isSelected: Boolean,
    isMatched: Boolean,
    onClick: (() -> Unit)?,
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isMatched -> MaterialTheme.colorScheme.surfaceVariant
            isSelected -> Terracotta.copy(alpha = 0.1f)
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(300),
        label = "match_bg",
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (isMatched) 0.3f else 1f)
            .then(if (isSelected) Modifier.border(2.dp, Terracotta, RoundedCornerShape(8.dp)) else Modifier)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(12.dp),
        )
    }
}
