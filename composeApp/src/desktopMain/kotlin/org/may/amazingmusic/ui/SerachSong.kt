package org.may.amazingmusic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.may.amazingmusic.bean.KuwoSong
import org.may.amazingmusic.theme.MyLightGray
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
fun SearchSong(modifier: Modifier, kuwoViewModel: KuwoViewModel, backClick: () -> Unit) {
    var inputText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val songListData by kuwoViewModel.kuwoSongs.collectAsState(arrayListOf())

    Card(modifier = modifier) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = backClick) {
                    Icon(Icons.AutoMirrored.Sharp.ArrowBack, "")
                }
                Box(
                    modifier = Modifier.weight(1f).padding(end = 10.dp).height(30.dp)
                        .background(color = MyLightGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    BasicTextField(
                        value = inputText,
                        onValueChange = {
                            inputText = it
                        },
                        modifier = Modifier.fillMaxWidth().padding(5.dp),
                        textStyle = TextStyle(fontSize = 14.sp),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions {
                            scope.launch {
                                kuwoViewModel.getSongBySearch(inputText, 1)
                                inputText = ""
                            }
                        }
                    )
                }

                Button(onClick = {
                    scope.launch {
                        kuwoViewModel.getSongBySearch(inputText, 1)
                        inputText = ""
                    }
                }) {
                    Text("Search")
                }
            }

            LazyColumn {
                if (!songListData.isNullOrEmpty()) {
                    items(songListData!!) { song ->
                        SongItem(song, kuwoViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SongItem(song: KuwoSong, kuwoViewModel: KuwoViewModel) {
    Card(modifier = Modifier.fillMaxWidth().height(80.dp), RoundedCornerShape(10.dp)) {
        Column(
            modifier = Modifier.fillMaxSize().clickable {
                kuwoViewModel.addToQueue(song)
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    load = { loadImg(song.pic) },
                    contentDescription = "",
                    modifier = Modifier.padding(5.dp).fillMaxHeight(),
                    shape = RoundedCornerShape(0),
                )
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text("${song.name}", fontSize = 15.sp, maxLines = 1)
                    Text("${song.artist}", fontSize = 12.sp, maxLines = 1)
                }
            }
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth().height(1.dp))
}
