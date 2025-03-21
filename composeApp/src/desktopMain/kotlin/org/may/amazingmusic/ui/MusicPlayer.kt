package org.may.amazingmusic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.may.amazingmusic.bean.KuwoSong
import java.net.URI

@Composable
fun MusicPlayer(modifier: Modifier, song: KuwoSong?) {
    var musicPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    if (!song?.url.isNullOrBlank()) {
        println(" !!!!!!!!!!!!!!!!!!!!!  ")
    }

    if (musicPlayer == null && !song?.url.isNullOrBlank()) {
        println("play song=${song?.name}")
        musicPlayer = MediaPlayer(Media(URI(song?.url).toString()))
        musicPlayer?.play()
        musicPlayer?.setOnPlaying {
            isPlaying = true
        }
        musicPlayer?.setOnPaused {
            isPlaying = false
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Play some music", modifier = Modifier.padding(bottom = 20.dp))

        if (!song?.pic.isNullOrBlank()) {

            AsyncImage(
                load = {
                    println("pic: ${song?.pic}")
                    loadImg(song?.pic)
                },
                contentDescription = "Test",
                modifier = Modifier.alpha(if (isPlaying) 1.0f else 0.0f).size(200.dp, 200.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = {
                musicPlayer?.pause()
            }) { Text("Pause") }
            Spacer(Modifier.width(10.dp))
            Button(onClick = {
                musicPlayer?.stop()
                musicPlayer = null
            }) { Text("Stop") }
        }

    }
}