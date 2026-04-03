package com.arameu.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Manuscript palette — terracotta, parchment, aged ink
val Terracotta = Color(0xFFC67B5C)
val TerracottaDark = Color(0xFFAD6548)
val Parchment = Color(0xFFF5ECD7)
val ParchmentLight = Color(0xFFFAF6EE)
val AgedInk = Color(0xFF4A3728)
val MutedGold = Color(0xFFBFA15E)
val WarmWhite = Color(0xFFFDF8F0)
val LighterParchment = Color(0xFFF0E4CA)

val ManuscriptLightColorScheme = lightColorScheme(
    primary = Terracotta,
    onPrimary = ParchmentLight,
    primaryContainer = TerracottaDark,
    onPrimaryContainer = ParchmentLight,
    secondary = MutedGold,
    onSecondary = AgedInk,
    secondaryContainer = LighterParchment,
    onSecondaryContainer = AgedInk,
    tertiary = TerracottaDark,
    onTertiary = ParchmentLight,
    background = Parchment,
    onBackground = AgedInk,
    surface = WarmWhite,
    onSurface = AgedInk,
    surfaceVariant = LighterParchment,
    onSurfaceVariant = AgedInk,
    outline = TerracottaDark,
    outlineVariant = LighterParchment,
)
