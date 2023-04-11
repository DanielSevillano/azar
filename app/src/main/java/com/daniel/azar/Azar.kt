package com.daniel.azar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daniel.azar.contenido.ContenidoDado
import com.daniel.azar.contenido.ContenidoLetra
import com.daniel.azar.contenido.ContenidoMoneda
import com.daniel.azar.contenido.ContenidoRango

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Azar(
    pantallaCompacta: Boolean,
    viewModel: AzarViewModel = viewModel()
) {
    val estadoPager = rememberPagerState()
    var historialAbierto by rememberSaveable { mutableStateOf(false) }
    var dialogoRangoAbierto by rememberSaveable { mutableStateOf(false) }
    val estadoHistorial = rememberModalBottomSheetState()
    val elementoSeleccionado = Elemento.values()[estadoPager.currentPage]

    Scaffold(
        topBar = {
            BarraSuperior(
                estadoPager = estadoPager,
                usarIconos = !pantallaCompacta
            )
        },
        bottomBar = {
            BarraInferior(
                elementoSeleccionado = Elemento.values()[estadoPager.currentPage],
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
            pageCount = Elemento.values().size,
            modifier = Modifier.padding(paddingValues),
            state = estadoPager
        ) { indice ->
            when (Elemento.values()[indice]) {
                Elemento.Dado -> ContenidoDado(
                    valoresElemento = viewModel.valoresDado,
                    representarValores = { n -> viewModel.representarDado(n) },
                    gradosRotacion = viewModel.gradosRotacion,
                    tirarElemento = { viewModel.tirarElemento(Elemento.Dado) }
                )

                Elemento.Moneda -> ContenidoMoneda(
                    valoresElemento = viewModel.valoresMoneda,
                    representarValores = { n -> viewModel.representarMoneda(n) },
                    gradosRotacion = viewModel.gradosRotacion,
                    tirarElemento = { viewModel.tirarElemento(Elemento.Moneda) }
                )

                Elemento.Rango -> ContenidoRango(
                    pantallaCompacta = pantallaCompacta,
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
            }
        }

        if (historialAbierto) {
            Historial(
                cerrarHistorial = { historialAbierto = false },
                estadoHistorial = estadoHistorial,
                elementoSeleccionado = Elemento.values()[estadoPager.currentPage],
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