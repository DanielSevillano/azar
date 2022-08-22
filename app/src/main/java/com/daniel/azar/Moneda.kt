package com.daniel.azar

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

val monedas = mapOf(
    1 to R.drawable.cara,
    2 to R.drawable.cruz
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Moneda() {
    var numeroMonedas: Int by remember { mutableStateOf(value = 1) }
    var valorMonedas: List<Int> by remember { mutableStateOf(listOf(0, 0, 0)) }

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
                        id = monedas.getOrDefault(
                            valorMoneda,
                            R.drawable.moneda
                        )
                    ),
                    contentDescription = "$valorMoneda",
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .widthIn(max = 200.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FilledIconButton(onClick = { valorMonedas = tirarMonedas() }) {
                Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = "Tirar moneda")
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

            FilledTonalIconButton(onClick = { valorMonedas = listOf(0, 0, 0) }) {
                Icon(imageVector = Icons.Filled.ClearAll, contentDescription = "Borrar")
            }
        }
    }
}

fun tirarMonedas(): MutableList<Int> {
    return numerosAleatorios(final = 2)
}