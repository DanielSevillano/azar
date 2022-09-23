package com.daniel.azar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dado(viewModel: DadoViewModel = viewModel()) {
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
            .clickable { viewModel.tirarDados() }
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            for (numeroDado in 0 until viewModel.numeroDados) {
                val valorDado = viewModel.valoresDados[numeroDado]
                Image(
                    painter = painterResource(id = viewModel.imagenDado(valorDado)),
                    contentDescription = "$valorDado",
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .widthIn(max = 200.dp)
                        .rotate(rotacion),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledIconButton(onClick = { viewModel.tirarDados() }) {
                Icon(
                    imageVector = Icons.Filled.Casino,
                    contentDescription = stringResource(id = R.string.tirar_dado)
                )
            }

            Row {
                for (dados in 1..3) {
                    FilterChip(selected = viewModel.numeroDados == dados,
                        onClick = { viewModel.numeroDados = dados },
                        label = {
                            Text(text = "$dados")
                        })
                }
            }

            FilledTonalIconButton(
                onClick = { viewModel.reiniciarDados() },
                enabled = viewModel.valoresDadosModificados()
            ) {
                Icon(
                    imageVector = Icons.Filled.ClearAll,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }
        }
    }
}