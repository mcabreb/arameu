package com.arameu.audio

object AudioPathResolver {

    fun resolve(audioId: String?, unitId: Int): String? {
        if (audioId == null) return null
        val slug = audioId.lowercase()
            .replace("_", "-")
            .replace(" ", "-")
        return "audio/unit$unitId/$slug.mp3"
    }
}
