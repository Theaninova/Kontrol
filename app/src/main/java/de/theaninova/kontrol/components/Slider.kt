package de.theaninova.kontrol.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import de.theaninova.kontrol.controls.schema.KontrolIconSet
import kotlin.math.min
import kotlin.math.max

@Composable
fun SuperSlider(
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    value: Float = .5F,
    height: Dp = 48.dp,
    onValueChange: (Float) -> Unit = {},
) {
    val endMin = with(LocalDensity.current) { height.toPx() }
    var parentWidth by remember { mutableStateOf(IntSize.Zero) }
    val endFraction by remember(parentWidth) {
        mutableStateOf(endMin / parentWidth.width)
    }
    val showTextOnLeft by remember(value) {
        mutableStateOf(value > .5F)
    }
    val color by animateColorAsState(
        if (enabled) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.surfaceVariant
    )
    val textColor by animateColorAsState(
        if (enabled) MaterialTheme.colorScheme.onSecondary
        else MaterialTheme.colorScheme.onSurfaceVariant
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .height(height)
            .onSizeChanged {
                parentWidth = it
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = height / 2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Surface(
                shape = RoundedCornerShape(percent = 100),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp,
                modifier = Modifier.height(4.dp).weight(1f)
            ) {

            }
            AnimatedVisibility(visible = !showTextOnLeft) {
                Text(label, Modifier.padding(start = 8.dp))
            }
        }
        Surface(
            shape = RoundedCornerShape(percent = 100),
            color = color,
            modifier = Modifier
                .height(height)
                .fillMaxWidth(max(min(value, 1F), endFraction))
                .draggable(
                    enabled = enabled,
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        onValueChange((delta / parentWidth.width) + value)
                    }
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(visible = showTextOnLeft) {
                    Text(
                        text = label,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )
                }
                Spacer(Modifier.width(0.dp))
                if (icon != null) {
                    Icon(
                        tint = textColor,
                        imageVector = icon,
                        contentDescription = label
                    )
                } else {
                    Row {
                        Surface(
                            modifier = Modifier.size(8.dp),
                            shape = CircleShape,
                            color = textColor,
                        ) {}
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun SuperSliderIcon() {
    SuperSlider(
        label = "Brightness",
        icon = KontrolIconSet.BrightnessAuto,
    )
}

@Preview
@Composable
fun SuperSliderProgressNone() {
    SuperSlider(
        label = "Brightness",
        value = 0F,
        icon = KontrolIconSet.BrightnessAuto,
    )
}

@Preview
@Composable
fun SuperSliderProgressFull() {
    SuperSlider(
        label = "Brightness",
        value = 1F,
        icon = KontrolIconSet.BrightnessAuto,
    )
}

@Preview
@Composable
fun SuperSliderNoIcon() {
    SuperSlider(label = "Brightness")
}
