package org.may.amazingmusic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import javafx.application.Platform
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.may.amazingmusic.viewmodel.KuwoViewModel
import java.net.URI

@Composable
fun MusicPlayer(kuwoViewModel: KuwoViewModel, openSongList : () -> Unit) {
    val currentSong by kuwoViewModel.currentSong.collectAsState()
    val isPlaying by kuwoViewModel.isPlaying
    val isError by kuwoViewModel.playError

    LaunchedEffect(currentSong) {
        currentSong?.let { song ->
            if (!song.url.isNullOrBlank()) {
                Platform.runLater {
                    kuwoViewModel.musicPlayer?.dispose()
                    kuwoViewModel.isPlaying.value = -1
                    kuwoViewModel.playError.value = false
                    kuwoViewModel.musicPlayer = MediaPlayer(Media(URI(song.url!!).toString())).apply {
                        setOnPlaying { kuwoViewModel.isPlaying.value = 1 }
                        setOnPaused { kuwoViewModel.isPlaying.value = 0 }
                        setOnStopped { kuwoViewModel.isPlaying.value = -1 }
                        setOnError { kuwoViewModel.playError.value = true }
                        setOnEndOfMedia { kuwoViewModel.handlePlaybackEnd() }
                        play()
                    }
                }
            }
        }
    }

    var loadImgDone by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(),) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            Text(text = "Play some music", modifier = Modifier.padding(bottom = 20.dp))

            println(" load ")
            if (isPlaying >= 0) {
                AsyncImage(
                    load = {
                        loadImg(currentSong?.pic)
                    },
                    contentDescription = "",
                    modifier = Modifier.size(200.dp, 200.dp),
                    shape = RoundedCornerShape(200.dp),
                )
            } else {
                Spacer(modifier = Modifier.size(200.dp, 200.dp))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = {
                    if (isPlaying == 1) kuwoViewModel.musicPlayer?.pause() else kuwoViewModel.musicPlayer?.play()
                }) { Text(if (isPlaying == 1) "Pause" else "Play") }
                Spacer(Modifier.width(10.dp))
                Button(onClick = {
                    kuwoViewModel.handlePlaybackEnd()
                }) { Text("NEXT") }
            }

            if (isError) {
                Text(text = "播放错误")
            }


        }
        IconButton(
            onClick = openSongList,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(Icons.Sharp.KeyboardArrowUp, "")
        }

    }

    DisposableEffect(Unit) {
        onDispose {
            kuwoViewModel.musicPlayer?.dispose()
            kuwoViewModel.musicPlayer = null
        }
    }
}