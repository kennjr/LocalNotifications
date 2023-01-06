package com.example.localnotifications.presentation.navigation

import com.example.localnotifications.common.Constants.DETAILS_SCREEN_ARG

sealed class Screen (val route: String){
    object Main: Screen("main")
    object Details: Screen("details/{$DETAILS_SCREEN_ARG}"){
        fun passArgument(message: String) = "details/$message"
    }
}