package org.may.amazingmusic.viewmodel

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.may.amazingmusic.bean.KuwoSong
import org.may.amazingmusic.network.NetWorkUtils

class KuwoViewModel{
    private val api = NetWorkUtils.getKuwoApi()

    val kuwoSongs = MutableSharedFlow<List<KuwoSong>?>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )
    suspend fun getSongBySearch(text: String?, page: Int) {
        if (text.isNullOrBlank()) return
        runCatching {
            val result = api.searchSongResult(text, page, 15)
            kuwoSongs.tryEmit(result.data)
        }.onFailure {
            it.printStackTrace()
            kuwoSongs.tryEmit(null)
        }
    }

    val songToPlay = MutableSharedFlow<KuwoSong?>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )
    fun playThisSong(song: KuwoSong) {
        println("play this song = $song")
        songToPlay.tryEmit(song)
    }

}