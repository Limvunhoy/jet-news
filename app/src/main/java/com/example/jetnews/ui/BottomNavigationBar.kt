/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.snippets.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.ui.AppNavHost

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    Home("home", "Home", Icons.Default.Home, "Home"),
    Profile("profile", "Profile", Icons.Default.Person, "Profile"),
}

@Composable
fun AppNavigation(
//    modifier: Modifier = Modifier,
    navController: NavHostController,
//    startDestination: Destination,
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    var currentRoute = currentDestination?.route
//    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
//        Destination.entries.forEachIndexed { index, destination ->
        Destination.entries.forEach {  destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        // Saves the state of the current destination before navigating away
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true // prevents multiple copies of the same destination
                        restoreState = true // restores previously saved state
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = destination.contentDescription,
                    )
                },
                label = {
                    Text(
                        destination.label
                    )
                },
            )
        }
    }
}

@Preview()
// [START android_compose_components_navigationbarexample]
@Composable
fun NavigationBarExample(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.Home

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AppNavigation(
//                modifier = modifier,
                navController = navController,
//                startDestination = startDestination
            )
        }
    ) { contentPadding ->
        AppNavHost(
            modifier = Modifier.padding(contentPadding),
            startDestination = startDestination,
            navController = navController
        )
    }
}
// [END android_compose_components_navigationbarexample]

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//// [START android_compose_components_navigationtabexample]
//@Composable
//fun NavigationTabExample(modifier: Modifier = Modifier) {
//    val navController = rememberNavController()
//    val startDestination = Destination.SONGS
//    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
//
//    Scaffold(modifier = modifier) { contentPadding ->
//        PrimaryTabRow(
//            selectedTabIndex = selectedDestination,
//            modifier = Modifier.padding(contentPadding)
//        ) {
//            Destination.entries.forEachIndexed { index, destination ->
//                Tab(
//                    selected = selectedDestination == index,
//                    onClick = {
//                        navController.navigate(route = destination.route)
//                        selectedDestination = index
//                    },
//                    text = {
//                        Text(
//                            text = destination.label,
//                            maxLines = 2,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                )
//            }
//        }
//        AppNavHost(navController, startDestination)
//    }
//}
// [END android_compose_components_navigationtabexample]