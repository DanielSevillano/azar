import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import azar.composeapp.generated.resources.Res
import azar.composeapp.generated.resources.borrar
import azar.composeapp.generated.resources.historial
import azar.composeapp.generated.resources.tirar_color
import azar.composeapp.generated.resources.tirar_dado
import azar.composeapp.generated.resources.tirar_letra
import azar.composeapp.generated.resources.tirar_moneda
import azar.composeapp.generated.resources.tirar_rango
import contenido.ContenidoColor
import contenido.ContenidoImagen
import contenido.ContenidoLetra
import contenido.ContenidoRango
import org.jetbrains.compose.resources.stringResource

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun Azar() {
    val viewModel = viewModel { AzarViewModel() }

    var historialAbierto by rememberSaveable { mutableStateOf(false) }
    var dialogoRangoAbierto by rememberSaveable { mutableStateOf(false) }
    val estadoHistorial = rememberModalBottomSheetState()
    var elementoSeleccionado by rememberSaveable { mutableStateOf(Elemento.Dado) }
    var mostrarMenu by rememberSaveable { mutableStateOf(value = false) }

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val layoutType = with(receiver = adaptiveInfo) {
        if (windowSizeClass.isWidthAtLeastBreakpoint(widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            Elemento.entries.forEach { elemento ->
                item(
                    selected = elemento == elementoSeleccionado,
                    onClick = { elementoSeleccionado = elemento },
                    icon = {
                        Icon(
                            imageVector = elemento.icono,
                            contentDescription = stringResource(resource = elemento.nombre)
                        )
                    },
                    label = {
                        Text(text = stringResource(resource = elemento.nombre))
                    }
                )
            }
        },
        layoutType = layoutType
    ) {
        Scaffold(
            floatingActionButton = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalFloatingToolbar(
                        expanded = true,
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = { viewModel.tirarElemento(elementoSeleccionado) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shuffle,
                                    contentDescription = when (elementoSeleccionado) {
                                        Elemento.Dado -> stringResource(resource = Res.string.tirar_dado)
                                        Elemento.Moneda -> stringResource(resource = Res.string.tirar_moneda)
                                        Elemento.Rango -> stringResource(resource = Res.string.tirar_rango)
                                        Elemento.Letra -> stringResource(resource = Res.string.tirar_letra)
                                        Elemento.Color -> stringResource(resource = Res.string.tirar_color)
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset(x = -ScreenOffset)
                            .zIndex(zIndex = 1f)
                    ) {
                        Box {
                            FilledIconButton(
                                onClick = { mostrarMenu = true }
                            ) {
                                Text(text = viewModel.numeroElementos.toString())
                            }

                            DropdownMenu(
                                expanded = mostrarMenu,
                                onDismissRequest = { mostrarMenu = false },
                                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                                shape = MaterialTheme.shapes.large
                            ) {
                                val listItemModifier = Modifier
                                    .padding(
                                        horizontal = 4.dp,
                                        vertical = 2.dp
                                    )
                                    .clip(shape = MaterialTheme.shapes.large)

                                listOf(1, 2, 3).forEach { numero ->
                                    val seleccionado = viewModel.numeroElementos == numero
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = numero.toString())
                                        },
                                        onClick = {
                                            mostrarMenu = false
                                            viewModel.numeroElementos = numero
                                        },
                                        modifier = if (seleccionado) {
                                            listItemModifier.background(color = MaterialTheme.colorScheme.primary)
                                        } else listItemModifier,
                                        colors = if (seleccionado) {
                                            MenuDefaults.itemColors(
                                                textColor = MaterialTheme.colorScheme.onPrimary
                                            )
                                        } else MenuDefaults.itemColors()
                                    )
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = elementoSeleccionado == Elemento.Rango
                        ) {
                            FilledTonalButton(
                                modifier = Modifier.padding(start = 2.dp),
                                onClick = { dialogoRangoAbierto = true }
                            ) {
                                Text(text = "${viewModel.inicioRango} - ${viewModel.finalRango}")
                            }
                        }

                        IconButton(
                            onClick = { historialAbierto = true }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.History,
                                contentDescription = stringResource(Res.string.historial)
                            )
                        }

                        AnimatedVisibility(
                            visible = !viewModel.valoresElemento(elementoSeleccionado)
                                .isNullOrEmpty()
                        ) {
                            IconButton(onClick = {
                                viewModel.borrarValoresElemento(
                                    elementoSeleccionado
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.ClearAll,
                                    contentDescription = stringResource(resource = Res.string.borrar)
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            ) {
                when (elementoSeleccionado) {
                    Elemento.Dado -> ContenidoImagen(
                        elemento = Elemento.Dado,
                        valoresElemento = viewModel.valoresDado,
                        representarValores = { n -> viewModel.representarDado(n) },
                        gradosRotacion = viewModel.gradosRotacion,
                        tirarElemento = { viewModel.tirarElemento(Elemento.Dado) }
                    )

                    Elemento.Moneda -> ContenidoImagen(
                        elemento = Elemento.Moneda,
                        valoresElemento = viewModel.valoresMoneda,
                        representarValores = { n -> viewModel.representarMoneda(n) },
                        gradosRotacion = viewModel.gradosRotacion,
                        tirarElemento = { viewModel.tirarElemento(Elemento.Moneda) }
                    )

                    Elemento.Rango -> ContenidoRango(
                        valoresElemento = viewModel.valoresRango,
                        gradosRotacion = viewModel.gradosRotacion,
                        tirarElemento = { viewModel.tirarElemento(Elemento.Rango) }
                    )

                    Elemento.Letra -> ContenidoLetra(
                        valoresElemento = viewModel.valoresLetra,
                        gradosRotacion = viewModel.gradosRotacion,
                        tirarElemento = { viewModel.tirarElemento(Elemento.Letra) }
                    )

                    Elemento.Color -> ContenidoColor(
                        valoresElemento = viewModel.valoresColor,
                        gradosRotacion = viewModel.gradosRotacion,
                        tirarElemento = { viewModel.tirarElemento(Elemento.Color) }
                    )
                }
            }
        }
    }

    if (historialAbierto) {
        Historial(
            cerrarHistorial = { historialAbierto = false },
            estadoHistorial = estadoHistorial,
            elementoSeleccionado = elementoSeleccionado,
            tiradasElemento = viewModel.tiradasElemento(elementoSeleccionado),
            representarTirada = viewModel.representarTirada(elementoSeleccionado),
            eliminarHistorial = { viewModel.eliminarHistorialElemento(elementoSeleccionado) }
        )
    }

    if (dialogoRangoAbierto) {
        DialogoRango(
            cerrarDialogo = { dialogoRangoAbierto = false },
            inicioRango = viewModel.inicioRango,
            finalRango = viewModel.finalRango,
            cambiarRango = { inicio, final -> viewModel.cambiarRango(inicio, final) },
        )
    }
}