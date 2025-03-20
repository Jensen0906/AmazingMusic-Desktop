package org.may.amazingmusic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import java.awt.Component
import java.io.IOException
import java.net.URL

@Composable
@Preview
fun App() {
    MaterialTheme {
        val mediaPlayerComponent = remember {
            NativeDiscovery().discover()
            CallbackMediaPlayerComponent()
        }
        val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }
        var playing by remember { mutableStateOf(0) }
        mediaPlayer.events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun playing(mediaPlayer: MediaPlayer?) {
                super.playing(mediaPlayer)
                playing = 1
            }

            override fun paused(mediaPlayer: MediaPlayer?) {
                super.paused(mediaPlayer)
                playing = -1
            }

            override fun stopped(mediaPlayer: MediaPlayer?) {
                super.stopped(mediaPlayer)
                playing = 0
            }
        })



        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            SwingPanel(
                modifier = Modifier.size(640.dp, 360.dp),
                factory = {
                    mediaPlayerComponent as Component
                }
            )
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    if (playing == 0) {
                        mediaPlayer.media().play("https://amazingmusic-1321711729.cos.ap-shanghai.myqcloud.com/video/fun_video.mp4")
//                        mediaPlayer.media().play("https://amazingmusic-1321711729.cos.ap-shanghai.myqcloud.com/DINOSAUR-AKMU.mp3")
                    } else if (playing == 1) {
                        mediaPlayer.controls().pause()
                    } else {
                        mediaPlayer.controls().play()
                    }
                }) {
                    Text(if (playing == 1) "Pause" else "Play")
                }

                Spacer(Modifier.width(8.dp))

                Button(onClick = {
                    mediaPlayer.controls().stop()
                }) {
                    Text("Stop")
                }
            }

//            AnimatedVisibility(
//                visible = showContent,
//                enter = slideInHorizontally(),
//                exit = slideOutHorizontally(targetOffsetX = { -600 })
//            ) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                    AsyncImage(
//                        load = { loadImg("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e4b534273adb4c64b65e4f02807b5f9b~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?") },
//                        painter = { BitmapPainter(it) },
//                        contentDescription = "Test",
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//                }
//            }
        }
    }
}
