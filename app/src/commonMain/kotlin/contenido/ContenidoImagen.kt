package contenido

import Elemento
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import azar.app.generated.resources.Res
import azar.app.generated.resources.elemento_dado
import azar.app.generated.resources.elemento_moneda
import azar.app.generated.resources.tirar_dado
import azar.app.generated.resources.tirar_moneda
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContenidoImagen(
    elemento: Elemento,
    valoresElemento: List<Int>?,
    representarValores: (Int) -> DrawableResource,
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
                    painter = painterResource(resource = representarValores(0)),
                    contentDescription = stringResource(
                        resource = if (elemento == Elemento.Dado)
                            Res.string.tirar_dado
                        else
                            Res.string.tirar_moneda
                    ),
                    modifier = modificador
                )
            } else {
                valoresElemento.forEach { valor ->
                    Image(
                        painter = painterResource(resource = representarValores(valor)),
                        contentDescription = stringResource(
                            resource = if (elemento == Elemento.Dado)
                                Res.string.elemento_dado
                            else
                                Res.string.elemento_moneda
                        ) + "$valor",
                        modifier = modificador
                    )
                }
            }
        }
    }
}