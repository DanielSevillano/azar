package com.daniel.azar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.daniel.azar.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val destinoActual = navBackStackEntry?.destination
                val dimensionesVentana = calculateWindowSizeClass(this)

                Scaffold(
                    bottomBar = {
                        if (dimensionesVentana.widthSizeClass == WindowWidthSizeClass.Compact) {
                            NavigationBar {
                                destinos.forEach { destino ->
                                    val destinoSeleccionado =
                                        destinoActual?.hierarchy?.any { it.route == destino.ruta } == true
                                    NavigationBarItem(
                                        selected = destinoSeleccionado,
                                        onClick = {
                                            navController.navigate(destino.ruta) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (destinoSeleccionado) destino.iconoSeleccionado else destino.icono,
                                                contentDescription = destino.ruta
                                            )
                                        },
                                        label = {
                                            Text(text = destino.ruta)
                                        })
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        when (dimensionesVentana.widthSizeClass) {
                            WindowWidthSizeClass.Expanded -> {
                                PermanentNavigationDrawer(drawerContent = {
                                    PermanentDrawerSheet(modifier = Modifier.padding(horizontal = 12.dp)) {
                                        Spacer(modifier = Modifier.height(8.dp))

                                        destinos.forEach { seccion ->
                                            val seleccionado =
                                                destinoActual?.hierarchy?.any { it.route == seccion.ruta } == true
                                            NavigationDrawerItem(
                                                selected = seleccionado,
                                                onClick = {
                                                    navController.navigate(seccion.ruta) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                icon = {
                                                    Icon(
                                                        imageVector = if (seleccionado) seccion.iconoSeleccionado else seccion.icono,
                                                        contentDescription = seccion.ruta,
                                                        tint = MaterialTheme.colorScheme.onBackground
                                                    )
                                                },
                                                label = {
                                                    Text(
                                                        text = seccion.ruta
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }) {
                                    Navegacion(navController = navController)
                                }
                            }
                            WindowWidthSizeClass.Medium -> {
                                Row {
                                    NavigationRail(modifier = Modifier.padding(top = 4.dp)) {
                                        Spacer(Modifier.weight(1f))

                                        destinos.forEach { seccion ->
                                            val seleccionado =
                                                destinoActual?.hierarchy?.any { it.route == seccion.ruta } == true
                                            NavigationRailItem(
                                                selected = seleccionado,
                                                onClick = {
                                                    navController.navigate(seccion.ruta) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                icon = {
                                                    Icon(
                                                        imageVector = if (seleccionado) seccion.iconoSeleccionado else seccion.icono,
                                                        contentDescription = seccion.ruta,
                                                        tint = MaterialTheme.colorScheme.onBackground
                                                    )
                                                },
                                                label = {
                                                    Text(
                                                        text = seccion.ruta
                                                    )
                                                }
                                            )
                                        }

                                        Spacer(Modifier.weight(1f))
                                    }

                                    Navegacion(navController = navController)
                                }
                            }
                            else -> {
                                Navegacion(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navegacion(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = destinos.first().ruta
    ) {
        composable("Dado") { Dado() }
        composable("Moneda") { Moneda() }
        composable("Rango") { Rango() }
    }
}

sealed class Destino(val ruta: String, val icono: ImageVector, val iconoSeleccionado: ImageVector) {
    object Dado : Destino("Dado", Icons.Outlined.Casino, Icons.Filled.Casino)
    object Moneda : Destino("Moneda", Icons.Outlined.MonetizationOn, Icons.Filled.MonetizationOn)
    object Rango : Destino("Rango", Icons.Outlined.Pin, Icons.Filled.Pin)
}

val destinos = listOf(
    Destino.Dado,
    Destino.Moneda,
    Destino.Rango
)

fun numerosAleatorios(inicio: Int = 1, final: Int): MutableList<Int> {
    val semilla = SimpleDateFormat("HHmmssSSS", Locale.FRENCH).format(Date()).toInt()
    val valoresAleatorios = mutableListOf<Int>()
    for (i in (1..3)) valoresAleatorios.add(Random(semilla * i).nextInt(inicio, final + 1))
    return valoresAleatorios
}