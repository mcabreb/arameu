package com.arameu.audio

import android.content.Context
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class AudioManager(context: Context) {

    private val player: ExoPlayer = ExoPlayer.Builder(context)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                .build(),
            true,
        )
        .build()

    fun play(assetPath: String) {
        try {
            player.stop()
            player.clearMediaItems()
            val uri = "asset:///$assetPath"
            player.setMediaItem(MediaItem.fromUri(uri))
            player.prepare()
            player.play()
        } catch (e: Exception) {
            Log.w(TAG, "Failed to play audio: $assetPath — ${e.message}")
        }
    }

    fun playSequential(assetPaths: List<String>) {
        try {
            player.stop()
            player.clearMediaItems()
            assetPaths.forEach { path ->
                player.addMediaItem(MediaItem.fromUri("asset:///$path"))
            }
            player.prepare()
            player.play()
        } catch (e: Exception) {
            Log.w(TAG, "Failed to play sequential audio: ${e.message}")
        }
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        player.release()
    }

    fun addListener(listener: Player.Listener) {
        player.addListener(listener)
    }

    companion object {
        private const val TAG = "AudioManager"
    }
}
