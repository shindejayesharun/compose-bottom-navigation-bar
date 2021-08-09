package com.shindejayesharun.composebottomnavigationbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shindejayesharun.composebottomnavigationbar.ui.theme.ComposeBottomNavigationBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBottomNavigationBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text("Compose Bottom Navigation Bar", fontSize = 18.sp)
        },
        backgroundColor = colorResource(id = R.color.design_default_color_primary_dark),
        contentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Book,
        NavigationItem.Profile
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.design_default_color_primary_dark),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route, onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { route->
                            popUpTo(route = route){
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                enabled = true,
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.route) },
                label = { Text(text = item.title) }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
   // BottomNavigationBar(navController = NavController(this))
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Navigation(navHostController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            Text("Home")
        }
        composable(NavigationItem.Book.route) {
            Text("Book")
        }
        composable(NavigationItem.Profile.route) {
            Text("Profilw")
        }
    }
}

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem(route = "home", R.drawable.ic_home, "Home")
    object Book : NavigationItem(route = "book", R.drawable.ic_book, "Book")
    object Profile : NavigationItem(route = "profile", R.drawable.ic_user, "Profile")
}