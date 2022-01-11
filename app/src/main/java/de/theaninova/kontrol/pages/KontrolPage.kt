package de.theaninova.kontrol.pages

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.statusBarsPadding
import de.theaninova.kontrol.components.InfoText
import de.theaninova.kontrol.controls.Kontrol
import de.theaninova.kontrol.controls.schema.KONTROL_TEST_ITEMS
import de.theaninova.kontrol.controls.schema.KontrolIconSet
import de.theaninova.kontrol.controls.schema.KontrolSection
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference

@ExperimentalMaterial3Api
@Composable
fun KontrolPage(item: KontrolSection, navigationController: NavController? = null) {
    val animationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(animationSpec)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(
                    item.title, style = MaterialTheme.typography.headlineMedium
                ) },
                actions = {
                    IconButton(onClick = { navigationController?.navigate("settings") }) {
                        Icon(imageVector = KontrolIconSet.Settings, contentDescription = "Settings")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigationController?.navigateUp() }) {
                        Icon(imageVector = KontrolIconSet.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Column(Modifier.padding(horizontal = 16.dp).fillMaxSize()) {
            var isEnabled by rememberBooleanPreference(
                keyName = item.title,
                initialValue = false,
                defaultValue = false
            )
            val color by animateColorAsState(
                if (isEnabled) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            )
            Box(
                Modifier
                    .toggleable(
                        value = isEnabled,
                        onValueChange = { isEnabled = it }
                    )
                    .background(
                        color = color,
                        shape = RoundedCornerShape(30.dp),
                    )
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Enable all effects",
                        fontSize = 20.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (item.description != null) {
                InfoText(item.description)
                Spacer(modifier = Modifier.height(24.dp))
            }

            for (kontrol in item.items) {
                Kontrol(item = kontrol, item.title)
                Spacer(Modifier.height(8.dp))
            }

        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun KontrolPagePreview() {
    KontrolPage(item = KONTROL_TEST_ITEMS.first())
}
