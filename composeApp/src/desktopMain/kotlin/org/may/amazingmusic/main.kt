package org.may.amazingmusic

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import javafx.application.Platform

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AmazingMusic",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ) {
        Platform.startup {
            println(" init startup")
        }
        App()
    }
}