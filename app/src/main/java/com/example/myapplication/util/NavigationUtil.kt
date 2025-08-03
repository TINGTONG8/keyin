package com.example.myapplication.util

import androidx.navigation.NavController

fun NavController.safeNavigate(route: String) {
    val currentRoute = this.currentDestination?.route
    if (currentRoute != route) {
        this.navigate(route)
    }
}