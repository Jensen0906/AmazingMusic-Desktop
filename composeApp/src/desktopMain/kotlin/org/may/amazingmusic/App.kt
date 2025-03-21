package org.may.amazingmusic

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.may.amazingmusic.bean.KuwoSong
import org.may.amazingmusic.ui.MusicPlayer
import org.may.amazingmusic.ui.SearchSong
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
@Preview
fun App() {
    val kuwoViewModel = KuwoViewModel()

    val song by kuwoViewModel.songToPlay.collectAsState(KuwoSong())
    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            SearchSong(
                modifier = Modifier.fillMaxHeight().width(300.dp),
                kuwoViewModel
            )
            MusicPlayer(
                modifier = Modifier.weight(1f).width(500.dp).fillMaxHeight(),
                song
            )
        }
    }
}
