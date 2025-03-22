package org.may.amazingmusic.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.may.amazingmusic.bean.KuwoSong
import org.may.amazingmusic.network.NetWorkUtils
import kotlin.random.Random

class KuwoViewModel {
    private val api = NetWorkUtils.getKuwoApi()

    private val _playlist = mutableStateListOf<KuwoSong>()
    val playlist: List<KuwoSong> get() = _playlist

    val currentIndex = mutableStateOf(-1)

    val playbackMode = mutableStateOf(0)

    var musicPlayer: MediaPlayer? = null

    val isPlaying = mutableStateOf(-1)

    val playError = mutableStateOf(false)

    val currentSong = MutableStateFlow<KuwoSong?>(null)


    val kuwoSongs = MutableSharedFlow<List<KuwoSong>?>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    suspend fun getSongBySearch(text: String?, page: Int) {
        if (text.isNullOrBlank()) return
        kuwoSongs.tryEmit(null)
        runCatching {
            val result = api.searchSongResult(text, page, 15)
            kuwoSongs.tryEmit(result.data)
        }.onFailure {
            it.printStackTrace()
            kuwoSongs.tryEmit(null)
        }
    }

    fun addToQueue(song: KuwoSong) {
        println("addToQueue this song = ${song.name}")
        if (!_playlist.contains(song)) {
            _playlist.add(song)
            if (currentIndex.value == -1) {
                currentIndex.value = 0
                currentSong.value = song
            }
        }
    }

    fun playAtIndex(index: Int) {
        if (index in _playlist.indices) {
            currentIndex.value = index
            currentSong.value = _playlist[index]
        }
    }

    fun handlePlaybackEnd() {
        when (playbackMode.value) {
            0 -> playAtIndex((currentIndex.value + 1) % _playlist.size)
            1 -> {
                musicPlayer?.run {
                    seek(Duration.ZERO)
                    play()
                }
            }

            2 -> playAtIndex(Random.nextInt(_playlist.size))
        }
    }

}