package org.may.amazingmusic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import java.io.IOException
import java.net.URL

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(
                visible = showContent,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally(targetOffsetX = { -600 })
            ) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                    AsyncImage(
//                        load = { loadImg("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e4b534273adb4c64b65e4f02807b5f9b~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?") },
//                        painter = { BitmapPainter(it) },
//                        contentDescription = "Test",
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
                    val mediaPlayerComponent = remember {
                        NativeDiscovery().discover()
                        CallbackMediaPlayerComponent().apply {
                            mediaPlayer().media()
                                .play("https://amazingmusic-1321711729.cos.ap-shanghai.myqcloud.com/video/fun_video.mp4")
                        }
                    }
                    DisposableEffect(Unit) {
                        onDispose {
                            mediaPlayerComponent.mediaPlayer().release()
                        }
                    }
                    mediaPlayerComponent.mediaPlayer().videoSurface().attachVideoSurface()
                }
            }
        }
    }
}

//@Composable
//fun VideoPlayer(mediaPlayerComponent: CallbackMediaPlayerComponent) {
//    Box(modifier = Modifier.fillMaxSize()) {
//        SwingPanel(
//            modifier = Modifier.align(Alignment.Center),
//            factory = {
//                mediaPlayerComponent.mediaPlayer().let { player ->
//                    player.videoSurface().attachVideoSurface()
//                    preferredSize = Dimension(1280, 720) // 设置初始分辨率
//                } as Component
//            }
//        )
//    }
//}

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
