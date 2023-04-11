package com.daniel.azar.contenido

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContenidoRango(
    pantallaCompacta: Boolean,
    valoresElemento: List<Int>?,
    gradosRotacion: Float,
    tirarElemento: () -> Unit,
    inicioRango: Int,
    finalRango: Int,
    abrirDialogoRango: () -> Unit
) {
    val rotacion by animateFloatAsState(targetValue = gradosRotacion)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FlowRow(
            modifier = if (pantallaCompacta) Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { tirarElemento() }
                .padding(16.dp)
                .align(Alignment.TopCenter) else Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { tirarElemento() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val modificador = Modifier
                .padding(horizontal = 4.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .graphicsLayer { rotationY = rotacion }
            if (valoresElemento.isNullOrEmpty()) {
                Text(
                    text = " ? ",
                    modifier = modificador,
                    style = MaterialTheme.typography.displayLarge
                )
            } else {
                valoresElemento.forEach { valor ->
                    Text(
                        text = " $valor ",
                        modifier = modificador,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }
        }

        Button(
            onClick = { abrirDialogoRango() },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 4.dp),
        ) {
            Text(text = "$inicioRango - $finalRango")
        }
    }
}