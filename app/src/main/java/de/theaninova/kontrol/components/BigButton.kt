package de.theaninova.kontrol.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun GiantButton(
    modifier: Modifier = Modifier,
    state: Boolean = true,
    onToggle: (Boolean) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    HapticToggleSurface(
        modifier = modifier,
        state = state,
        onToggle = onToggle,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            content = content,
        )
    }
}
