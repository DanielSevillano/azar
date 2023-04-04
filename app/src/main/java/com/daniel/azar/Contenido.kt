package com.daniel.azar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Contenido(
    valoresEspaciado: PaddingValues,
    elementoSeleccionado: Elemento,
    valoresElemento: List<Int>?,
    representarValores: (Int) -> Int,
    gradosRotacion: Float,
    tirarElemento: () -> Unit
) {
    val rotacion by animateFloatAsState(targetValue = gradosRotacion)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(valoresEspaciado),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = if (valoresElemento.isNullOrEmpty()) Modifier
            else Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { tirarElemento() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (valoresElemento.isNullOrEmpty()) {
                Bienvenida(
                    elementoSeleccionado = elementoSeleccionado,
                    tirarElemento = tirarElemento
                )
            } else {
                valoresElemento.forEach { valor ->
                    when (elementoSeleccionado) {
                        Elemento.Dado -> {
                            Image(
                                painter = painterResource(id = representarValores(valor)),
                                contentDescription = stringResource(id = R.string.elemento_dado) + "$valor",
                                modifier = Modifier
                                    .weight(1f, fill = false)
                                    .widthIn(max = 200.dp)
                                    .rotate(rotacion)
                            )
                        }

                        Elemento.Moneda -> {
                            Image(
                                painter = painterResource(id = representarValores(valor)),
                                contentDescription = stringResource(id = R.string.elemento_moneda) + "$valor",
                                modifier = Modifier
                                    .weight(1f, fill = false)
                                    .widthIn(max = 200.dp)
                                    .graphicsLayer { rotationX = rotacion }
                            )
                        }

                        Elemento.Rango -> {
                            Text(
                                text = " $valor ",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .graphicsLayer { rotationY = rotacion },
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            }

        }
    }
}