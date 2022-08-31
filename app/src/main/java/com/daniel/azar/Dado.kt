package com.daniel.azar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dado() {
    var numeroDados: Int by remember { mutableStateOf(value = 1) }
    var valorDados: List<Int> by remember { mutableStateOf(listOf(0, 0, 0)) }

    val imagenesDados = mapOf(
        1 to R.drawable.dado_1,
        2 to R.drawable.dado_2,
        3 to R.drawable.dado_3,
        4 to R.drawable.dado_4,
        5 to R.drawable.dado_5,
        6 to R.drawable.dado_6
    )

    fun tirarDados(): List<Int> {
        return numerosAleatorios(final = 6)
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
            .clickable { valorDados = tirarDados() }
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            for (numeroDado in 0 until numeroDados) {
                val valorDado = valorDados[numeroDado]
                Image(
                    painter = painterResource(
                        id = imagenesDados.getOrDefault(
                            valorDado,
                            R.drawable.dado_0
                        )
                    ),
                    contentDescription = "$valorDado",
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .widthIn(max = 200.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FilledIconButton(onClick = { valorDados = tirarDados() }) {
                Icon(
                    imageVector = Icons.Filled.Casino,
                    contentDescription = stringResource(id = R.string.tirar_dado)
                )
            }

            Row {
                for (dados in 1..3) {
                    FilterChip(
                        selected = numeroDados == dados,
                        onClick = { numeroDados = dados },
                        label = {
                            Text(text = "$dados")
                        })
                }
            }

            FilledTonalIconButton(onClick = { valorDados = listOf(0, 0, 0) }) {
                Icon(
                    imageVector = Icons.Filled.ClearAll,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }
        }
    }
}