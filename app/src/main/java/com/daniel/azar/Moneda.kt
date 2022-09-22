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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Moneda(viewModel: AzarViewModel = viewModel()) {
    val rotacion by animateFloatAsState(targetValue = viewModel.gradosRotacion)

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
            .clickable { viewModel.tirarMonedas() }
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            for (numeroMoneda in 0 until viewModel.numeroMonedas) {
                val valorMoneda = viewModel.valoresMonedas[numeroMoneda]
                Image(
                    painter = painterResource(
                        id = viewModel.imagenMoneda(valorMoneda)
                    ),
                    contentDescription = "$valorMoneda",
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .widthIn(max = 200.dp)
                        .graphicsLayer { rotationX = rotacion },
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FilledIconButton(onClick = { viewModel.tirarMonedas() }) {
                Icon(
                    imageVector = Icons.Filled.MonetizationOn,
                    contentDescription = stringResource(id = R.string.tirar_moneda)
                )
            }

            Row {
                for (monedas in 1..3) {
                    FilterChip(
                        selected = viewModel.numeroMonedas == monedas,
                        onClick = { viewModel.numeroMonedas = monedas },
                        label = {
                            Text(text = "$monedas")
                        })
                }
            }

            FilledTonalIconButton(
                onClick = { viewModel.reiniciarMonedas() },
                enabled = viewModel.valoresMonedasModificados()
            ) {
                Icon(
                    imageVector = Icons.Filled.ClearAll,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }
        }
    }
}