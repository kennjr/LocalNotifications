package com.example.localnotifications.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.localnotifications.common.Constants
import com.example.localnotifications.presentation.main.DetailScreen
import com.example.localnotifications.presentation.main.MainScreen

@Composable
fun SetupNavGraph( navHostController: NavHostController ) {

    NavHost(navController = navHostController, startDestination = Screen.Main.route, builder = {
        composable(route = Screen.Main.route){
            MainScreen(navController = navHostController)
        }
        composable(route = Screen.Details.route, arguments = listOf(navArgument(Constants.DETAILS_SCREEN_ARG, builder = {
            type = NavType.StringType
        })), deepLinks = listOf(navDeepLink { uriPattern = "${Constants.APP_URL}/${Constants.DETAILS_SCREEN_ARG}={${Constants.DETAILS_SCREEN_ARG}}" })){
            val arguments = it.arguments
            arguments?.getString(Constants.DETAILS_SCREEN_ARG)?.let { message ->
                DetailScreen(argument = message)
            }
        }
    })

}