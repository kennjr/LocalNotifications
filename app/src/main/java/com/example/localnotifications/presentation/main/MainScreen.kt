package com.example.localnotifications.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.example.localnotifications.R
import com.example.localnotifications.presentation.navigation.Screen

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()) {
    Box(modifier = Modifier
        .fillMaxHeight(.9f)
        .fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            /**
             * The btn for showing a simple notification
             */
            Button(onClick = { mainViewModel.showSimpleNotification() }) {
                Text(text = stringResource(id = R.string.simple_notification))
            }

            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            /**
             * The btn for updating the notification
             */
            Button(onClick = { mainViewModel.updateNotification() }) {
                Text(text = stringResource(id = R.string.update_notification))
            }

            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            /**
             * The btn for showing the progress notification
             */
            Button(onClick = { mainViewModel.showProgressNotification() }) {
                Text(text = stringResource(id = R.string.progress_notification))
            }
            /**
             * The btn for canceling the notification
             */
            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            Button(onClick = { mainViewModel.removeNotification() }) {
                Text(text = stringResource(id = R.string.cancel_notification))
            }
            /**
             * The btn for navigating to the details screen
             */
            Spacer(modifier = Modifier.fillMaxHeight(.05f))
            Button(onClick = { navController.navigate( Screen.Details.passArgument("Coming from the main screen") ) }) {
                Text(text = stringResource(id = R.string.details_screen))
            }

        }
    }
}