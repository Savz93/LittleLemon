package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.composables.Home
import com.example.littlelemon.composables.OnBoarding
import com.example.littlelemon.composables.Profile

@Composable
fun Navigation(navController: NavHostController) {

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination =
        if (!context.getSharedPreferences("user", Context.MODE_PRIVATE).contains("first_name")) Destinations.OnBoardingScreen.route
        else Destinations.HomeScreen.route
    ) {
        composable(Destinations.OnBoardingScreen.route) { OnBoarding(navController) }
        composable(Destinations.HomeScreen.route) { Home() }
        composable(Destinations.ProfileScreen.route) { Profile() }
    }
}