package de.theaninova.kontrol.controls

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.theaninova.kontrol.components.SuperSlider
import de.theaninova.kontrol.controls.schema.KontrolCheckbox
import de.theaninova.kontrol.controls.schema.KontrolItem
import de.theaninova.kontrol.controls.schema.KontrolSeparator
import de.theaninova.kontrol.controls.schema.KontrolSlider
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference
import dev.burnoo.compose.rememberpreference.rememberFloatPreference

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun Kontrol(item: KontrolItem, enabled: Boolean, parentKey: String) {
    when (item) {
        is KontrolSeparator -> Row {
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.name,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
        }
        is KontrolCheckbox -> KontrolLabel(
            item = item,
            row = {
                var state by rememberBooleanPreference(
                    keyName = "$parentKey.${item.name}",
                    initialValue = false,
                    defaultValue = false,
                )
                Checkbox(checked = state, onCheckedChange = { state = it })
            }
        )
        is KontrolSlider -> {
            var state by rememberFloatPreference(
                keyName = "$parentKey.${item.name}",
                initialValue = 0F,
                defaultValue = 0F,
            )
            SuperSlider(
                label = item.name,
                icon = item.icon?.icon,
                enabled = enabled,
                value = state,
                onValueChange = { state = it }
            )
        }
    }
}

@Composable
fun KontrolLabel(
    item: KontrolItem,
    row: @Composable () -> Unit = {},
    column: @Composable () -> Unit = {},
) {
    Row(verticalAlignment = Alignment.Top) {
        if (item.icon != null) {
            Icon(
                imageVector = item.icon!!.icon,
                contentDescription = item.icon?.name,
                modifier = Modifier.width(32.dp),
            )
        } else {
            Spacer(Modifier.width(32.dp))
        }
        Spacer(Modifier.width(8.dp))
        Column {
            Text(text = item.name)
            column()
        }
        row()
    }
}
