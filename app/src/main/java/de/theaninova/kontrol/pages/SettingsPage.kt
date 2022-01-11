package de.theaninova.kontrol.pages

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import de.theaninova.kontrol.components.ErrorText
import de.theaninova.kontrol.components.InfoText
import de.theaninova.kontrol.controls.schema.KontrolIconSet
import de.theaninova.kontrol.controls.schema.KontrolSection
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun SettingsPage(navigationController: NavController? = null) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text("Settings", fontSize = 40.sp) },
                navigationIcon = {
                    IconButton(onClick = { navigationController?.navigateUp() }) {
                        Icon(imageVector = KontrolIconSet.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            ImportStructureButton()
        }
    }
}

const val KONTROL_STRUCTURE_KEY = "KONTROL_STRUCTURE"

@ExperimentalPermissionsApi
@Composable
fun ImportStructureButton() {
    Column {
        val filesPermissionState =
            rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        PermissionRequired(
            permissionState = filesPermissionState,
            permissionNotGrantedContent = {},
            permissionNotAvailableContent = {}) {

        }
        var importError by remember {
            mutableStateOf<String?>(null)
        }

        var kontrolStructure by rememberStringPreference(
            keyName = KONTROL_STRUCTURE_KEY,
            initialValue = null,
            defaultValue = null,
        )
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { result ->
            context.contentResolver.openInputStream(
                result ?: return@rememberLauncherForActivityResult
            )
                ?.bufferedReader()
                ?.readText()
                ?.let { json ->
                    try {
                        Json.decodeFromString<List<KontrolSection>>(json)

                        kontrolStructure = json
                    } catch(exception: Exception) {
                        importError = exception.localizedMessage
                    }
                }
        }

        if (kontrolStructure == "[]") {
            InfoText("No files have been imported yet")
            Spacer(Modifier.height(8.dp))
        }

        var isExpanded by remember { mutableStateOf(false) }
        Row {
            OutlinedButton(onClick = {
                filesPermissionState.launchPermissionRequest()
                launcher.launch("application/json")
            }) {
                Text("Import")
            }
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = { isExpanded = !isExpanded }, enabled = kontrolStructure != "[]") {
                Text("${if (isExpanded) "Hide" else "Show"} JSON")
            }
        }


        if (importError != null) {
            Spacer(Modifier.height(8.dp))
            ErrorText("Invalid File:\n\n$importError")
        }
        if (isExpanded) {
            InfoText("$kontrolStructure")
        }
    }
}

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun SettingsPagePreview() {
    SettingsPage()
}
