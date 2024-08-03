package com.example.apppruebatecnica.Core.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apppruebatecnica.Core.Presentation.ui.theme.AppPruebaTecnicaTheme
import com.example.apppruebatecnica.Login.Presentation.LoginScreen
import com.example.apppruebatecnica.SingUp.Presentation.SingUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPruebaTecnicaTheme {
                Navigation()
            }
        }
    }

    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screens.Login
        ) {

            composable<Screens.Login> {
                LoginScreen(
                    onNavigate = {
                        navController.navigate(Screens.SingUp)
                    },
                    navController
                )
            }

            composable<Screens.SingUp> {
                SingUpScreen(
                    onNavigate = {
                        navController.popBackStack()
                    },
                    navController
                )
            }
        }
    }
}
