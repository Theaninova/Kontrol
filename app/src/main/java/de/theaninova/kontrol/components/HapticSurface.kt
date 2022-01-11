package de.theaninova.kontrol.components

import android.view.HapticFeedbackConstants
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun HapticToggleSurface(
    modifier: Modifier = Modifier,
    state: Boolean = true,
    enabled: Boolean = true,
    onCornerRadius: Dp = 30.dp,
    offCornerRadius: Dp = 16.dp,
    onToggle: (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    val color by animateColorAsState(
        when {
            !enabled -> MaterialTheme.colorScheme.surfaceVariant
            state -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.surface
        }
    )
    val interactionSource = remember { MutableInteractionSource() }
    val view = LocalView.current
    var size by remember { mutableStateOf(onCornerRadius) }
    val cornerRadius by animateDpAsState(size)
    Surface(
        modifier = modifier
            .pointerInput(interactionSource, state) {
                detectTapGestures(
                    onPress = {
                        size = offCornerRadius
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        if (tryAwaitRelease()) onToggle(!state)
                        size = onCornerRadius
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY_RELEASE)
                    },
                )
            },
        shape = RoundedCornerShape(cornerRadius),
        color = color,
        content = content,
        tonalElevation = 2.dp
    )
}