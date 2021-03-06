package de.theaninova.kontrol.pages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.theaninova.kontrol.components.HapticToggleSurface
import de.theaninova.kontrol.controls.schema.KontrolIconSet
import de.theaninova.kontrol.controls.schema.KontrolSection
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun HomePage(navController: NavController? = null) {
    val animationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(animationSpec)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text("Kontrol", fontSize = 40.sp) },
                actions = {
                    IconButton(onClick = { navController?.navigate("settings") }) {
                        Icon(imageVector = KontrolIconSet.Settings, contentDescription = "Settings")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        val kontrolStructureJson by rememberStringPreference(
            keyName = KONTROL_STRUCTURE_KEY,
            initialValue = "nil",
            defaultValue = null
        )

        when (kontrolStructureJson) {
            null -> {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    ImportStructureButton()
                }
            }
            "nil" -> {
                // TODO: loading
            }
            else -> {
                val kontrolStructure = remember(kontrolStructureJson) {
                    Json.decodeFromString<List<KontrolSection>>(kontrolStructureJson!!)
                }
                val scrollState = rememberScrollState()

                LazyColumn(
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.systemBars,
                        applyTop = true,
                        applyBottom = true,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .scrollable(scrollState, Orientation.Vertical)
                ) {
                    items(kontrolStructure) { item ->
                        val isEnabled by rememberBooleanPreference(
                            keyName = item.title,
                            initialValue = false,
                            defaultValue = false
                        )

                        HapticToggleSurface(
                            modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
                            onCornerRadius = 24.dp,
                            offCornerRadius = 12.dp,
                            state = isEnabled,
                            onToggle = { navController?.navigate(item.title) },
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.headlineMedium,
                                )
                                if (item.description != null) {
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        text = item.description,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@Preview
@ExperimentalMaterial3Api
@Composable
fun HomePagePreview() {
    HomePage()
}
