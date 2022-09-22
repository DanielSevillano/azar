package com.daniel.azar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.daniel.azar.ui.theme.AppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberAnimatedNavController()

                Scaffold(bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val destinoActual = navBackStackEntry?.destination

                        Destino.values().forEach { destino ->
                            val destinoSeleccionado =
                                destinoActual?.hierarchy?.any { it.route == destino.ruta } == true
                            NavigationBarItem(selected = destinoSeleccionado, onClick = {
                                navController.navigate(destino.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }, icon = {
                                Icon(
                                    imageVector = if (destinoSeleccionado) destino.iconoSeleccionado else destino.icono,
                                    contentDescription = stringResource(id = destino.nombreId)
                                )
                            }, label = {
                                Text(text = stringResource(id = destino.nombreId))
                            })
                        }
                    }
                }) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Destino.values().first().ruta
                        ) {
                            composable(Destino.Dado.ruta) { Dado() }
                            composable(Destino.Moneda.ruta) { Moneda() }
                            composable(Destino.Rango.ruta) { Rango() }
                        }
                    }
                }
            }
        }
    }
}