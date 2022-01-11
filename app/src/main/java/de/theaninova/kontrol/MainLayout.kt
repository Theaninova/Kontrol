package de.theaninova.kontrol

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.theaninova.kontrol.controls.schema.KontrolSection
import de.theaninova.kontrol.pages.HomePage
import de.theaninova.kontrol.pages.KONTROL_STRUCTURE_KEY
import de.theaninova.kontrol.pages.KontrolPage
import de.theaninova.kontrol.pages.SettingsPage
import de.theaninova.kontrol.ui.theme.KontrolTheme
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun MainLayout() {
    val navController = rememberNavController()
    val kontrolStructureJson by rememberStringPreference(
        keyName = KONTROL_STRUCTURE_KEY,
        initialValue = "",
        defaultValue = null,
    )
    val kontrolStructure = remember(kontrolStructureJson) {
        try {
            Json.decodeFromString<List<KontrolSection>>(kontrolStructureJson!!)
        } catch (e: Exception) {
            null
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") { HomePage(navController) }
        composable("settings") { SettingsPage(navController) }

        if (kontrolStructure != null) {
            for (navDestination in kontrolStructure) {
                composable(navDestination.title) {
                    KontrolPage(
                        item = navDestination,
                        navigationController = navController,
                    )
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KontrolTheme {
        MainLayout()
    }
}
