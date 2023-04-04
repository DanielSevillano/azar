package com.daniel.azar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.SettingsEthernet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BarraInferior(
    elementoSeleccionado: Elemento,
    numeroSeleccionado: Numero,
    tirarElemento: () -> Unit,
    cambiarNumeroSeleccionado: (Numero) -> Unit,
    abrirHistorial: () -> Unit,
    abrirDialogoRango: () -> Unit
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = {
                cambiarNumeroSeleccionado(when(numeroSeleccionado) {
                    Numero.Uno -> Numero.Dos
                    Numero.Dos -> Numero.Tres
                    Numero.Tres -> Numero.Uno
                })
            }) {
                Icon(
                    imageVector = numeroSeleccionado.icono,
                    contentDescription = stringResource(id = numeroSeleccionado.texto)
                )
            }

            IconButton(onClick = { abrirHistorial() }) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = stringResource(id = R.string.historial)
                )
            }

            if(elementoSeleccionado == Elemento.Rango) {
                IconButton(onClick = { abrirDialogoRango() }) {
                    Icon(
                        imageVector = Icons.Outlined.SettingsEthernet,
                        contentDescription = stringResource(id = R.string.rango_definir)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { tirarElemento() },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = when(elementoSeleccionado) {
                        Elemento.Dado -> stringResource(id = R.string.tirar_dado)
                        Elemento.Moneda -> stringResource(id = R.string.tirar_moneda)
                        Elemento.Rango -> stringResource(id = R.string.tirar_rango)
                    }
                )
            }
        }
    )
}