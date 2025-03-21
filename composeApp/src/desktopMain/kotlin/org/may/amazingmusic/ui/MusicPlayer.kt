package org.may.amazingmusic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javafx.application.Platform
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.may.amazingmusic.bean.KuwoSong
import java.net.URI

@Composable
fun MusicPlayer(modifier: Modifier, song: KuwoSong?) {
    var musicPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(-1) }

    LaunchedEffect(song?.url) {
        if (song?.url.isNullOrBlank()) return@LaunchedEffect
        Platform.runLater {
            musicPlayer?.dispose()
            isPlaying = -1
            musicPlayer = MediaPlayer(Media(URI(song?.url!!).toString())).apply {
                setOnPlaying { isPlaying = 1 }
                setOnPaused { isPlaying = 0 }
                setOnStopped { isPlaying = -1 }
                play()
            }
        }


    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Play some music", modifier = Modifier.padding(bottom = 20.dp))

        if (isPlaying >= 0) {
            AsyncImage(
                load = {
                    loadImg(song?.pic)
                },
                contentDescription = "Test",
                modifier = Modifier.size(200.dp, 200.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(200.dp, 200.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = {
                if (isPlaying == 1) musicPlayer?.pause() else musicPlayer?.play()
            }) { Text(if (isPlaying == 1) "Pause" else "Play") }
            Spacer(Modifier.width(10.dp))
            Button(onClick = {
                musicPlayer?.stop()
            }) { Text("Stop") }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            musicPlayer?.dispose()
            musicPlayer = null
        }
    }
}