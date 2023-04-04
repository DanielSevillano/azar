package com.daniel.azar

import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BarraSuperior(
    elementoSeleccionado: Elemento,
    cambiarElementoSeleccionado: (Elemento) -> Unit
) {
    TabRow(selectedTabIndex = Elemento.values().indexOf(elementoSeleccionado)) {
        Elemento.values().forEachIndexed { index, elemento ->
            Tab(
                selected = Elemento.values()[index] == elementoSeleccionado,
                onClick = { cambiarElementoSeleccionado(elemento) },
                text = { Text(text = stringResource(id = elemento.nombre)) },
                icon = {
                    Icon(
                        imageVector = elemento.icono,
                        contentDescription = stringResource(id = elemento.nombre)
                    )
                }
            )
        }
    }
}