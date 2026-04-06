package com.arameu.audio

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AudioManagerTest {

    @Test
    fun `resolveAssetPath builds correct path for simple id`() {
        val path = AudioPathResolver.resolve("a_aleph", 1)
        assertEquals("audio/unit1/a-aleph.mp3", path)
    }

    @Test
    fun `resolveAssetPath handles underscores in id`() {
        val path = AudioPathResolver.resolve("a_beth", 1)
        assertEquals("audio/unit1/a-beth.mp3", path)
    }

    @Test
    fun `resolveAssetPath handles null audio id`() {
        val path = AudioPathResolver.resolve(null, 1)
        assertNull(path)
    }

    @Test
    fun `resolveAssetPath handles different unit ids`() {
        val path = AudioPathResolver.resolve("a_teth", 2)
        assertEquals("audio/unit2/a-teth.mp3", path)
    }

    @Test
    fun `resolveAssetPath preserves special characters in slug`() {
        val path = AudioPathResolver.resolve("a_e", 2)
        assertEquals("audio/unit2/a-e.mp3", path)
    }
}
