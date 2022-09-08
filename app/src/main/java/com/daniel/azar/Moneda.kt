package com.daniel.azar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Moneda() {
    val valoresIniciales = listOf(0, 0, 0)

    var numeroMonedas: Int by remember { mutableStateOf(value = 1) }
    var valorMonedas: List<Int> by remember { mutableStateOf(valoresIniciales) }

    var gradosRotacion by remember { mutableStateOf(0f) }
    val rotacion by animateFloatAsState(targetValue = gradosRotacion)

    val imagenesMonedas = mapOf(
        1 to R.drawable.cara,
        2 to R.drawable.cruz
    )

    fun tirarMonedas(): List<Int> {
        gradosRotacion += 360f
        return numerosAleatorios(final = 2)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(1.dp))

        Row(modifier = Modifier
            .weight(1f, fill = false)
            .clip(RoundedCornerShape(20.dp))
            .clickable { valorMonedas = tirarMonedas() }
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            for (numeroMoneda in 0 until numeroMonedas) {
                val valorMoneda = valorMonedas[numeroMoneda]
                Image(
                    painter = painterResource(
                        id = imagenesMonedas.getOrDefault(
                            valorMoneda,
                            R.drawable.moneda
                        )
                    ),
                    contentDescription = "$valorMoneda",
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .widthIn(max = 200.dp)
                        .rotate(rotacion),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FilledIconButton(onClick = { valorMonedas = tirarMonedas() }) {
                Icon(
                    imageVector = Icons.Filled.MonetizationOn,
                    contentDescription = stringResource(id = R.string.tirar_moneda)
                )
            }

            Row {
                for (monedas in 1..3) {
                    FilterChip(
                        selected = numeroMonedas == monedas,
                        onClick = { numeroMonedas = monedas },
                        label = {
                            Text(text = "$monedas")
                        })
                }
            }

            FilledTonalIconButton(
                onClick = { valorMonedas = valoresIniciales },
                enabled = valorMonedas != valoresIniciales
            ) {
                Icon(
                    imageVector = Icons.Filled.ClearAll,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }
        }
    }
}