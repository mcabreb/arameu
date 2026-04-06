package com.arameu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val contentPadding: Dp = 24.dp,
    val cardSpacing: Dp = 16.dp,
    val touchTarget: Dp = 48.dp,
    val sectionSpacing: Dp = 32.dp,
    val elementSpacing: Dp = 12.dp,
)

val ArameuSpacing = Spacing()

val LocalSpacing = staticCompositionLocalOf { ArameuSpacing }
