package com.daniel.azar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
fun Bienvenida(
    elementoSeleccionado: Elemento,
    tirarElemento: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (LocalTime.now().hour) {
                in 6..12 -> stringResource(id = R.string.buenos_dias)
                in 12..20 -> stringResource(id = R.string.buenas_tardes)
                else -> stringResource(id = R.string.buenas_noches)
            },
            style = MaterialTheme.typography.titleLarge
        )

        ExtendedFloatingActionButton(
            onClick = { tirarElemento() },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Shuffle,
                    contentDescription = when (elementoSeleccionado) {
                        Elemento.Dado -> stringResource(id = R.string.tirar_dado)
                        Elemento.Moneda -> stringResource(id = R.string.tirar_moneda)
                        Elemento.Rango -> stringResource(id = R.string.tirar_rango)
                    }
                )
            },
            text = {
                Text(
                    text = when (elementoSeleccionado) {
                        Elemento.Dado -> stringResource(id = R.string.tirar_dado)
                        Elemento.Moneda -> stringResource(id = R.string.tirar_moneda)
                        Elemento.Rango -> stringResource(id = R.string.tirar_rango)
                    }
                )
            }
        )
    }
}