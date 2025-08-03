package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.home.HomePage
import com.example.myapplication.user.UserPage

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    SystemUiController(useDarkIcons = true)

    val navController = rememberNavController()
    val userRepository = (LocalContext.current.applicationContext as MyApplication)
    val application = (LocalContext.current.applicationContext as Application)
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(userRepository.repository, application)
    )

    NavHost(
        navController = navController,
        startDestination = "home_page_route"
    ) {
        composable("home_page_route") {
            HomePage(navController = navController, userViewModel = userViewModel)
        }

        composable("user_page_route") {
            UserPage(navController = navController, userViewModel = userViewModel)
        }
    }
}