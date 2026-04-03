package com.arameu.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ThemeTest {

    @Test
    fun `terracotta primary colour is defined`() {
        assertEquals(Color(0xFFC67B5C), Terracotta)
    }

    @Test
    fun `parchment background colour is defined`() {
        assertEquals(Color(0xFFF5ECD7), Parchment)
    }

    @Test
    fun `aged ink text colour is defined`() {
        assertEquals(Color(0xFF4A3728), AgedInk)
    }

    @Test
    fun `muted gold accent colour is defined`() {
        assertEquals(Color(0xFFBFA15E), MutedGold)
    }

    @Test
    fun `manuscript colour scheme uses terracotta as primary`() {
        assertEquals(Terracotta, ManuscriptLightColorScheme.primary)
    }

    @Test
    fun `manuscript colour scheme uses parchment as background`() {
        assertEquals(Parchment, ManuscriptLightColorScheme.background)
    }

    @Test
    fun `manuscript colour scheme uses aged ink for text`() {
        assertEquals(AgedInk, ManuscriptLightColorScheme.onBackground)
    }

    @Test
    fun `body typography has generous line height`() {
        val bodyStyle = ArameuTypography.bodyLarge
        assertTrue(
            "Body line height (${bodyStyle.lineHeight}) should be >= 25.6sp (1.6x 16sp)",
            bodyStyle.lineHeight.value >= 25.6f
        )
    }

    @Test
    fun `display typography for Aramaic has minimum 24sp`() {
        val displayStyle = ArameuTypography.displayLarge
        assertTrue(
            "Aramaic display font size (${displayStyle.fontSize}) should be >= 24sp",
            displayStyle.fontSize.value >= 24f
        )
    }

    @Test
    fun `content padding is at least 24dp`() {
        assertTrue(
            "Content padding should be >= 24dp",
            ArameuSpacing.contentPadding.value >= 24f
        )
    }

    @Test
    fun `card spacing is at least 16dp`() {
        assertTrue(
            "Card spacing should be >= 16dp",
            ArameuSpacing.cardSpacing.value >= 16f
        )
    }

    @Test
    fun `touch target is at least 48dp`() {
        assertTrue(
            "Touch target should be >= 48dp",
            ArameuSpacing.touchTarget.value >= 48f
        )
    }
}
