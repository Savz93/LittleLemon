package com.example.littlelemon

sealed class Destinations(val route: String) {
    object OnBoardingScreen: Destinations("on_boarding_screen")
    object HomeScreen: Destinations("home_screen")
    object ProfileScreen: Destinations("profile_screen")
}