package com.example.littlelemonrestaurant

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemonrestaurant.components.LITTLE_LEMON
import com.example.littlelemonrestaurant.components.LOGIN_STATUS
import com.example.littlelemonrestaurant.screens.Home
import com.example.littlelemonrestaurant.screens.Onboarding
import com.example.littlelemonrestaurant.screens.Profile

@Composable
fun MyNavigation(
    navController: NavHostController,
    menuItems: List<MenuItemRoom>
) {
    val context = LocalContext.current

    val sharedPref = context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.getBoolean(LOGIN_STATUS, false)

    val startDestination = if (isLoggedIn) Home.route else Onboarding.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Home.route) {
            Home(navController, menuItems)
        }
        composable(Profile.route) {
            Profile(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
    }
}