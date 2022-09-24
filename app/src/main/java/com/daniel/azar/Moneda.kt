package com.daniel.azar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Moneda(viewModel: MonedaViewModel = viewModel()) {
    val rotacion by animateFloatAsState(targetValue = viewModel.gradosRotacion)
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            Surface(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.History,
                                contentDescription = stringResource(id = R.string.historial)
                            )
                            Text(
                                text = stringResource(id = R.string.historial),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    if (viewModel.tiradasMoneda.isEmpty()) {
                        item {
                            Text(
                                text = "No hay historial",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    items(viewModel.tiradasMoneda.reversed()) { tirada ->
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            for (n in 0 until tirada.numeroElementos) {
                                val valorMoneda = tirada.valoresObtenidos[n]
                                Image(
                                    painter = painterResource(
                                        id = viewModel.imagenMoneda(
                                            valorMoneda
                                        )
                                    ),
                                    contentDescription = "$valorMoneda",
                                    modifier = Modifier.widthIn(max = 60.dp),
                                    alignment = Alignment.Center,
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
            }

        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
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

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    for (monedas in 1..3) {
                        FilterChip(
                            selected = viewModel.numeroMonedas == monedas,
                            onClick = { viewModel.numeroMonedas = monedas },
                            label = {
                                Text(text = "$monedas")
                            },
                            shape = when (monedas) {
                                1 -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                                3 -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                                else -> CutCornerShape(0.dp)
                            }
                        )
                    }
                }

                Row {
                    OutlinedIconButton(
                        onClick = { viewModel.reiniciarMonedas() },
                        enabled = viewModel.valoresMonedasModificados()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ClearAll,
                            contentDescription = stringResource(id = R.string.borrar)
                        )
                    }

                    FilledTonalIconButton(onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = stringResource(id = R.string.historial)
                        )
                    }

                    FilledIconButton(onClick = { viewModel.tirarMonedas() }) {
                        Icon(
                            imageVector = Icons.Filled.MonetizationOn,
                            contentDescription = stringResource(id = R.string.tirar_moneda)
                        )
                    }
                }
            }
        }
    }
}