package org.may.amazingmusic.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.may.amazingmusic.viewmodel.KuwoViewModel

@Composable
fun ShowSongList(
    modifier: Modifier,
    kuwoViewModel: KuwoViewModel,
    closeThisTab : () -> Unit
) {

    Box(modifier = modifier) {
        IconButton(
            onClick = closeThisTab,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(Icons.Sharp.KeyboardArrowDown, "")
        }
    }
}