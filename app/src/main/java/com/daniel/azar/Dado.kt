package com.daniel.azar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    val bottomSheetState = rememberModalBottomSheetState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

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
            Row {
                for (dados in 1..3) {
                    FilterChip(
                        selected = viewModel.numeroDados == dados,
                        onClick = { viewModel.numeroDados = dados },
                        label = {
                            Text(text = "$dados")
                        },
                        shape = when (dados) {
                            1 -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            3 -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            else -> CutCornerShape(0.dp)
                        }
                    )
                }
            }

            Row {
                OutlinedIconButton(
                    onClick = { viewModel.reiniciarDados() },
                    enabled = viewModel.valoresDadosModificados()
                ) {
                    Icon(
                        imageVector = Icons.Filled.ClearAll,
                        contentDescription = stringResource(id = R.string.borrar)
                    )
                }

                FilledTonalIconButton(onClick = { openBottomSheet = !openBottomSheet }) {
                    Icon(
                        imageVector = Icons.Filled.History,
                        contentDescription = stringResource(id = R.string.historial)
                    )
                }

                FilledIconButton(onClick = { viewModel.tirarDados() }) {
                    Icon(
                        imageVector = Icons.Filled.Casino,
                        contentDescription = stringResource(id = R.string.tirar_dado)
                    )
                }
            }
        }
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            Historial(
                destino = Destino.Dado,
                tiradas = viewModel.tiradasDado
            ) { n -> viewModel.imagenDado(n) }
        }
    }
}