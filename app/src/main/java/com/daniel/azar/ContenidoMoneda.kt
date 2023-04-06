package com.daniel.azar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ContenidoMoneda(
    valoresElemento: List<Int>?,
    representarValores: (Int) -> Int,
    gradosRotacion: Float,
    tirarElemento: () -> Unit
) {
    val rotacion by animateFloatAsState(targetValue = gradosRotacion)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { tirarElemento() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val modificador = Modifier
                .weight(1f, fill = false)
                .widthIn(max = 200.dp)
                .rotate(rotacion)

            if (valoresElemento.isNullOrEmpty()) {
                Image(
                    painter = painterResource(id = representarValores(0)),
                    contentDescription = stringResource(id = R.string.tirar_moneda),
                    modifier = modificador
                )
            } else {
                valoresElemento.forEach { valor ->
                    Image(
                        painter = painterResource(id = representarValores(valor)),
                        contentDescription = stringResource(id = R.string.elemento_moneda) + "$valor",
                        modifier = modificador
                    )
                }
            }
        }
    }
}