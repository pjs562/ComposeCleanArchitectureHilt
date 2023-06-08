package com.example.composecleanarchitecturehilt.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.example.composecleanarchitecturehilt.ui.home.HomeScreen
import com.example.composecleanarchitecturehilt.ui.navigation.HomeSections
import com.example.composecleanarchitecturehilt.ui.search.SearchListScreen
import com.example.composecleanarchitecturehilt.ui.search.SearchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp() {
    val navController = rememberNavController()

    val items = HomeSections.values().toList()
    var selectedItem by rememberSaveable(key = "MainAppSelectedItem") { mutableStateOf(items.first()) }
    var query by rememberSaveable(key = "MainAppQuery") { mutableStateOf("") }
    val shouldShowNavigationBar =
        navController.currentBackStackEntryAsState().value?.destination?.route == "home"
    Scaffold(modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            MainNavHost(
                navController = navController,
                selectedItem = selectedItem,
                query = query,
                onItemSelected = { selectedItem = it },
                onQueryChanged = { query = it },
                paddingValues = paddingValues
            )
        },
        bottomBar = {
            if (shouldShowNavigationBar)
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (item == selectedItem) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = stringResource(item.iconTextId)
                                )
                            },
                            label = {
                                Text(text = stringResource(item.titleTextId))
                            },
                            onClick = {
                                if (item == HomeSections.SEARCH && query.isEmpty())
                                    navController.navigate("searchList")
                                else
                                    selectedItem = item
                            },
                            selected = item == selectedItem
                        )
                    }
                }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavHost(
    navController: NavHostController,
    selectedItem: HomeSections,
    query: String,
    onItemSelected: (HomeSections) -> Unit,
    onQueryChanged: (String) -> Unit,
    paddingValues: PaddingValues,
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            when (selectedItem) {
                HomeSections.HOME -> HomeScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )

                HomeSections.SEARCH -> SearchScreen(
                    navController = navController,
                    query = query,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        composable("searchList") {
            SearchListScreen(navController, onItemSelected, onQueryChanged)
        }
    }
}