package com.daniel.azar.contenido

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContenidoColor(
    valoresElemento: List<Int>?,
    gradosRotacion: Float,
    tirarElemento: () -> Unit
) {
    val rotacion by animateFloatAsState(targetValue = gradosRotacion)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FlowRow(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { tirarElemento() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val modificador = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium)
                .graphicsLayer { rotationY = rotacion }

            if (valoresElemento.isNullOrEmpty()) {
                Box(modifier = modificador.background(MaterialTheme.colorScheme.primaryContainer))
            } else {
                valoresElemento.forEach { valor ->
                    Box(modifier = modificador.background(Color(color = valor + 4278190080)))
                }
            }
        }
    }
}