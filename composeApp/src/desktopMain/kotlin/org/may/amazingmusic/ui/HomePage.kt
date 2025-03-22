package org.may.amazingmusic.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
fun HomePage(
    modifier: Modifier,
    kuwoViewModel: KuwoViewModel,
    onSearchClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Sharp.Search, "")
            }

            IconButton(onClick = onRefreshClick) {
                Icon(Icons.Sharp.Refresh, "")
            }
        }
    }
}