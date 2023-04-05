package com.daniel.azar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Azar(viewModel: AzarViewModel = viewModel()) {
    var historialAbierto by rememberSaveable { mutableStateOf(false) }
    var dialogoRangoAbierto by rememberSaveable { mutableStateOf(false) }
    val estadoHistorial = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            BarraSuperior(
                elementoSeleccionado = viewModel.elementoSeleccionado,
                cambiarElementoSeleccionado = { elemento ->
                    viewModel.elementoSeleccionado = elemento
                }
            )
        },
        bottomBar = {
            BarraInferior(
                elementoSeleccionado = viewModel.elementoSeleccionado,
                numeroSeleccionado = viewModel.numeroElementos,
                tirarElemento = { viewModel.tirarElemento() },
                cambiarNumeroSeleccionado = { numero -> viewModel.numeroElementos = numero },
                abrirHistorial = { historialAbierto = true },
                abrirDialogoRango = { dialogoRangoAbierto = true },
                valoresElemento = viewModel.valoresElemento(viewModel.elementoSeleccionado),
                borrarValores = { viewModel.borrarValoresElemento() }
            )
        }
    ) { paddingValues ->
        Contenido(
            valoresEspaciado = paddingValues,
            elementoSeleccionado = viewModel.elementoSeleccionado,
            valoresElemento = viewModel.valoresElemento(viewModel.elementoSeleccionado),
            representarValores = viewModel.representarTirada(viewModel.elementoSeleccionado),
            gradosRotacion = viewModel.gradosRotacion
        ) { viewModel.tirarElemento() }

        if (historialAbierto) {
            Historial(
                cerrarHistorial = { historialAbierto = false },
                estadoHistorial = estadoHistorial,
                elementoSeleccionado = viewModel.elementoSeleccionado,
                tiradasElemento = viewModel.tiradasElemento(viewModel.elementoSeleccionado),
                representarTirada = viewModel.representarTirada(viewModel.elementoSeleccionado),
                eliminarHistorial = { viewModel.eliminarHistorialElemento(viewModel.elementoSeleccionado) }
            )
        }

        if (dialogoRangoAbierto) {
            DialogoRango(
                cerrarDialogo = { dialogoRangoAbierto = false },
                inicioRango = viewModel.inicioRango,
                finalRango = viewModel.finalRango,
                cambiarInicioRango = { n -> viewModel.cambiarInicioRango(n) },
                cambiarFinalRango = { n -> viewModel.cambiarFinalRango(n) }
            )
        }
    }
}