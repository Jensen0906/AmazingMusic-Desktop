package org.may.amazingmusic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import javafx.application.Platform
import javafx.scene.media.MediaPlayer
import javafx.scene.media.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.IOException
import java.net.URI
import java.net.URL

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AmazingMusic",
    ) {
//        App()
        MusicPlayer()
    }
}

@Composable
fun MusicPlayer() {
    Platform.startup {
        println(" init startup")
    }

    var musicPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val musicUrl = "https://kw-api.cenguigui.cn?id=1245780&type=song&level=exhigh&format=mp3"
    val musicPic = "https://img4.kuwo.cn/star/albumcover/600/s4s99/25/598328738.jpg"
    var picVisible by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Play some music", modifier = Modifier.padding(bottom = 20.dp))

            if (picVisible) {
                AsyncImage(
                    load = { loadImg(musicPic) },
                    painter = { BitmapPainter(it) },
                    contentDescription = "Test",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = {
                    if (musicPlayer == null) {
                        musicPlayer = MediaPlayer(Media(URI(musicUrl).toString()))
                    }
                    musicPlayer?.play()
                    picVisible = true
                }) {
                    Text("Play")
                }
                Spacer(Modifier.width(10.dp))
                Button(onClick = {
                    musicPlayer?.pause()
                }) { Text("Pause") }
                Spacer(Modifier.width(10.dp))
                Button(onClick = {
                    musicPlayer?.stop()
                    musicPlayer = null
                    picVisible = false
                }) { Text("Stop") }
            }
        }
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painter: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val img: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
    if (img != null) {
        Image(
            painter = painter(img!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
fun loadImg(url: String): ImageBitmap = URL(url).openStream().buffered().readAllBytes().decodeToImageBitmap()