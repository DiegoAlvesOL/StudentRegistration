package com.diegoalves.studentregistration.ui.navigation

import androidx.compose.runtime.Composable
import com.diegoalves.studentregistration.ui.screens.HomeScreen
import com.diegoalves.studentregistration.ui.screens.RegistrationScreen
import com.diegoalves.studentregistration.ui.screens.SearchScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation(){

    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = AppDestinations.HOME
    ){
        composable(AppDestinations.HOME){
            HomeScreen(
                onNavigateToRegistration = {
                    navigationController.navigate(AppDestinations.REGISTRATION)
                },
                onNavigateToSearch = {
                    navigationController.navigate(AppDestinations.SEARCH)
                }
            )
        }
        composable(AppDestinations.REGISTRATION){
            RegistrationScreen (
                onNavigateBack = { navigationController.popBackStack()}
            )
        }
        composable(AppDestinations.SEARCH){
            SearchScreen (
                onNavigateBack = {navigationController.popBackStack()}
            )
        }
    }

}