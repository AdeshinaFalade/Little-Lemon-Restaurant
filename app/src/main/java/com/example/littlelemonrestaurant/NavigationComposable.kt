package com.example.littlelemonrestaurant

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
    appDatabase: AppDatabase
) {
    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(appDatabase))

    val sharedPref = context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.getBoolean(LOGIN_STATUS, false)

    val startDestination = if (isLoggedIn) Home.route else Onboarding.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Home.route) {
            Home(navController, viewModel)
        }
        composable(Profile.route) {
            Profile(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
    }
}