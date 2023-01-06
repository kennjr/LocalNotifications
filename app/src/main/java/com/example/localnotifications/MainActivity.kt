package com.example.localnotifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.localnotifications.presentation.main.MainScreen
import com.example.localnotifications.presentation.navigation.SetupNavGraph
import com.example.localnotifications.presentation.ui.theme.LocalNotificationsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationService = CounterNotificationService(applicationContext)
        setContent {
            LocalNotificationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
//                        Box(modifier = Modifier
//                            .fillMaxHeight(.1f)
//                            .fillMaxWidth(),
//                            contentAlignment = Alignment.Center){
//                            Button(onClick = {
//                                notificationService.showNotification(Counter.value)
//                            }) {
//                                Text(text = "Show Notification")
//                            }
//                        }
                        SetupNavGraph(navHostController = rememberNavController())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LocalNotificationsTheme {
        Greeting("Android")
    }
}