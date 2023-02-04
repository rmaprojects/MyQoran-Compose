package com.rmaproject.myqoran.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyQoranDrawer(
    currentRoute: String,
    closeDrawer: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAdzanSchedule: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        MyQoranLogo(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 24.dp
                )
        )
        NavigationDrawerItem(
            label = { Text("Home") },
            selected = currentRoute == Screen.Home.route,
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            },
            onClick = {
                navigateToHome(); closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = currentRoute == Screen.Settings.route,
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings Icon"
                )
            },
            onClick = {
                navigateToSettings(); closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Jadwal Sholat") },
            selected = currentRoute == Screen.AdzanSchedule.route,
            icon = {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = "Clock Icon"
                )
            },
            onClick = {
                navigateToAdzanSchedule(); closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}


@Composable
fun MyQoranLogo(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.myqoransvglogo),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Inside
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = "MyQoran",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "by RMA Projects",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun MyQoranDrawerPreview() {
    MyQoranDrawer("home", {}, {}, {}, {})
}