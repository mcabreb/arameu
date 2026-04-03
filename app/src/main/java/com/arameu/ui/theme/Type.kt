package com.arameu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arameu.R

val NotoSansHebrew = FontFamily(
    Font(R.font.noto_sans_hebrew_regular, FontWeight.Normal),
    Font(R.font.noto_sans_hebrew_bold, FontWeight.Bold),
)

val ArameuTypography = Typography(
    // Display — used for Aramaic script (large, with Noto Sans Hebrew)
    displayLarge = TextStyle(
        fontFamily = NotoSansHebrew,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 64.8.sp, // 1.8x
    ),
    displayMedium = TextStyle(
        fontFamily = NotoSansHebrew,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 50.4.sp, // 1.8x
    ),
    displaySmall = TextStyle(
        fontFamily = NotoSansHebrew,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 43.2.sp, // 1.8x
    ),
    // Headlines — warm serif feel via default Serif family
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 44.8.sp, // 1.6x
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 38.4.sp, // 1.6x
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 32.sp, // 1.6x
    ),
    // Title
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 32.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 25.6.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.4.sp,
    ),
    // Body — generous line height (1.6x)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 25.6.sp, // 1.6x
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.4.sp, // 1.6x
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 19.2.sp, // 1.6x
        letterSpacing = 0.4.sp,
    ),
    // Label
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)
