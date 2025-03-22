package org.may.amazingmusic.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
fun RightBox(
    modifier: Modifier,
    kuwoViewModel: KuwoViewModel
) {

    val density = LocalDensity.current
    var showSongList by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier
    ) {
        MusicPlayer(kuwoViewModel) {
            showSongList = true
        }

        AnimatedVisibility(
            visible = showSongList,
            enter = slideInVertically {
                with(density) { 800.dp.roundToPx() }
            },
            exit = slideOutVertically {
                with(density) { 800.dp.roundToPx() }
            }
        ) {
            ShowSongList(
                Modifier.fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                kuwoViewModel
            ) {
                showSongList = false
            }
        }
    }
}