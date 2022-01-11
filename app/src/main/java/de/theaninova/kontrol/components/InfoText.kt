package de.theaninova.kontrol.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import de.theaninova.kontrol.controls.schema.KontrolIconSet

@Composable
fun InfoText(
    text: String,
    imageVector: ImageVector = KontrolIconSet.Info,
    contentDescription: String = "Info",
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Row {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = tint
        )
    }
}

@Composable
fun ErrorText(
    text: String,
    imageVector: ImageVector = KontrolIconSet.Error,
    contentDescription: String = "Error",
    tint: Color = MaterialTheme.colorScheme.error,
) {
    InfoText(text, imageVector, contentDescription, tint)
}
