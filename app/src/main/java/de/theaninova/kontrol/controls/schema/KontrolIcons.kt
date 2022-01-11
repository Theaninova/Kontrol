package de.theaninova.kontrol.controls.schema

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

val KontrolIconSet = Icons.Filled

@Serializable
enum class KontrolIcons(@Transient val icon: ImageVector) {
    SettingsBrightness(KontrolIconSet.SettingsBrightness),
    VolumeUp(KontrolIconSet.VolumeUp),
    VolumeDown(KontrolIconSet.VolumeDown),
    Equalizer(KontrolIconSet.Equalizer),
    AutoAwesome(KontrolIconSet.AutoAwesome),
    HdrPlus(KontrolIconSet.HdrPlus),
    HdrWeak(KontrolIconSet.HdrWeak),
    HdrStrong(KontrolIconSet.HdrStrong),
    AutoFpsSelect(KontrolIconSet.AutofpsSelect),
    ViewInAr(KontrolIconSet.ViewInAr),
    UnfoldMore(KontrolIconSet.UnfoldMore),
    UnfoldLess(KontrolIconSet.UnfoldLess),
    LocationSearching(KontrolIconSet.LocationSearching),
}
