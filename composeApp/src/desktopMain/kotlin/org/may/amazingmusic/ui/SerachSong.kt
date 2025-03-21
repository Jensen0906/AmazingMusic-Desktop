package org.may.amazingmusic.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.may.amazingmusic.bean.KuwoSong
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
fun SearchSong(modifier: Modifier, kuwoViewModel: KuwoViewModel) {
    var inputText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val songListData by kuwoViewModel.kuwoSongs.collectAsState(arrayListOf())
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                modifier = Modifier.weight(1f).padding(5.dp),
                textStyle = TextStyle(fontSize = 14.sp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions {
                    println("KeyboardActions")
                }
            )
            Button(onClick = {
                scope.launch {
                    println("获取")
                    kuwoViewModel.getSongBySearch(inputText, 1)
                    inputText = ""
                }
            }) {
                Text("Search")
            }
        }
        Text("Result", modifier = Modifier.fillMaxWidth(), fontSize = 15.sp)
        LazyColumn {
            println("songlist=$songListData")
            if (!songListData.isNullOrEmpty()) {
                println("songlist2=$songListData")
                items(songListData!!) { song ->
                    println("song=${song.name}")
                    SongItem(song, kuwoViewModel)
                }
            }
        }
    }
}

@Composable
fun SongItem(song: KuwoSong, kuwoViewModel: KuwoViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().height(80.dp).clickable {
            kuwoViewModel.playThisSong(song)
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                load = { loadImg(song.pic) },
                contentDescription = "Test",
                modifier = Modifier.padding(5.dp).fillMaxHeight()
            )
            Column(modifier = Modifier.fillMaxHeight()) {
                Text("${song.name}", fontSize = 15.sp, maxLines = 1)
                Text("${song.artist}", fontSize = 12.sp, maxLines = 1)
            }
        }
        Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp))
    }

}