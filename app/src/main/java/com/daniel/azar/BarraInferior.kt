package com.daniel.azar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BarraInferior(
    elementoSeleccionado: Elemento,
    numeroSeleccionado: Numero,
    tirarElemento: (Elemento) -> Unit,
    cambiarNumeroSeleccionado: (Numero) -> Unit,
    abrirHistorial: () -> Unit,
    valoresElemento: List<Int>?,
    borrarValores: (Elemento) -> Unit
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = {
                cambiarNumeroSeleccionado(
                    when (numeroSeleccionado) {
                        Numero.Uno -> Numero.Dos
                        Numero.Dos -> Numero.Tres
                        Numero.Tres -> Numero.Uno
                    }
                )
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

            IconButton(
                onClick = { borrarValores(elementoSeleccionado) },
                enabled = !valoresElemento.isNullOrEmpty()
            ) {
                Icon(
                    imageVector = Icons.Outlined.ClearAll,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(
                            id = when (elementoSeleccionado) {
                                Elemento.Dado -> R.string.tirar_dado
                                Elemento.Moneda -> R.string.tirar_moneda
                                Elemento.Rango -> R.string.tirar_rango
                            }
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = when (elementoSeleccionado) {
                            Elemento.Dado -> stringResource(id = R.string.tirar_dado)
                            Elemento.Moneda -> stringResource(id = R.string.tirar_moneda)
                            Elemento.Rango -> stringResource(id = R.string.tirar_rango)
                        }
                    )
                },
                onClick = { tirarElemento(elementoSeleccionado) },
                expanded = valoresElemento.isNullOrEmpty(),
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            )
        }
    )
}