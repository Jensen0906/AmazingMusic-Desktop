package org.may.amazingmusic.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
fun LeftBox(kuwoViewModel: KuwoViewModel) {

    var showSearch by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        Modifier.fillMaxHeight().width(300.dp).padding(end = 10.dp)
    ) {
        HomePage(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
            kuwoViewModel = kuwoViewModel,
            onSearchClick = {
                showSearch = true
            },
            onRefreshClick = {
                // Todo
            }
        )

        AnimatedVisibility(
            visible = showSearch,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            SearchSong(
                Modifier.fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                kuwoViewModel
            ) {
                showSearch = false
            }
        }
    }
}