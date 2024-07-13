import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import contenido.ContenidoColor
import contenido.ContenidoImagen
import contenido.ContenidoLetra
import contenido.ContenidoRango

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Azar() {
    val viewModel = viewModel<AzarViewModel>()
    val pagerState = rememberPagerState(pageCount = { Elemento.entries.size })
    var historialAbierto by rememberSaveable { mutableStateOf(false) }
    var dialogoRangoAbierto by rememberSaveable { mutableStateOf(false) }
    val estadoHistorial = rememberModalBottomSheetState()
    val elementoSeleccionado = Elemento.entries[pagerState.currentPage]

    Scaffold(
        topBar = {
            BarraSuperior(
                estadoPager = pagerState
            )
        },
        bottomBar = {
            BarraInferior(
                elementoSeleccionado = Elemento.entries[pagerState.currentPage],
                numeroSeleccionado = viewModel.numeroElementos,
                tirarElemento = { elemento -> viewModel.tirarElemento(elemento) },
                cambiarNumeroSeleccionado = { numero -> viewModel.numeroElementos = numero },
                abrirHistorial = { historialAbierto = true },
                valoresElemento = viewModel.valoresElemento(elementoSeleccionado),
                borrarValores = { elemento -> viewModel.borrarValoresElemento(elemento) }
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount < 0) historialAbierto = true
                    }
                }
        ) { indice ->
            when (Elemento.entries[indice]) {
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
                    tirarElemento = { viewModel.tirarElemento(Elemento.Rango) },
                    inicioRango = viewModel.inicioRango,
                    finalRango = viewModel.finalRango,
                    abrirDialogoRango = { dialogoRangoAbierto = true }
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

        if (historialAbierto) {
            Historial(
                cerrarHistorial = { historialAbierto = false },
                estadoHistorial = estadoHistorial,
                elementoSeleccionado = Elemento.entries[pagerState.currentPage],
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
}