package contenido

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
    valoresElemento: List<Int>?,
    gradosRotacion: Float,
    tirarElemento: () -> Unit
) {
    val rotacion by animateFloatAsState(
        targetValue = gradosRotacion,
        label = "rotacion"
    )

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
    }
}