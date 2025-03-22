package org.may.amazingmusic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.may.amazingmusic.theme.AllBackground
import org.may.amazingmusic.theme.AmazingMusicTheme
import org.may.amazingmusic.ui.LeftBox
import org.may.amazingmusic.ui.RightBox
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
@Preview
fun App() {
    val kuwoViewModel = KuwoViewModel()


    AmazingMusicTheme {
        Row(modifier = Modifier.fillMaxSize().background(color = AllBackground).padding(10.dp)) {

            LeftBox(kuwoViewModel)

            RightBox(modifier = Modifier.weight(1f).width(500.dp),kuwoViewModel)
        }
    }
}
