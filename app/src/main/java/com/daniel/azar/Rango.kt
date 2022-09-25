package com.daniel.azar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Rango(viewModel: RangoViewModel = viewModel()) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            Historial(destino = Destino.Rango, tiradas = viewModel.tiradasRango, cerrarHistorial = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            })
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
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                val focusManager = LocalFocusManager.current

                TextField(
                    value = viewModel.textoInicio,
                    onValueChange = { viewModel.textoInicio = it },
                    modifier = Modifier.weight(1F),
                    placeholder = { Text(text = stringResource(id = R.string.rango_inicio)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    singleLine = true,
                    shape = RoundedCornerShape(percent = 50),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                TextField(
                    value = viewModel.textoFinal,
                    onValueChange = { viewModel.textoFinal = it },
                    modifier = Modifier.weight(1F),
                    placeholder = { Text(text = stringResource(id = R.string.rango_final)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    singleLine = true,
                    shape = RoundedCornerShape(percent = 50),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                FilledTonalIconButton(
                    onClick = { viewModel.reiniciarTextos() }, enabled = viewModel.rangoDefinido()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(id = R.string.borrar)
                    )
                }

                Column {
                    FilledIconButton(onClick = { viewModel.desplegableExpandido = true }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = stringResource(id = R.string.desplegar)
                        )
                    }

                    DropdownMenu(expanded = viewModel.desplegableExpandido,
                        onDismissRequest = { viewModel.desplegableExpandido = false }) {
                        for (pareja in viewModel.opcionesDesplegable) {
                            DropdownMenuItem(text = { Text(text = "${pareja.first()} — ${pareja.last()}") },
                                onClick = {
                                    viewModel.textoInicio = pareja.first().toString()
                                    viewModel.textoFinal = pareja.last().toString()
                                    viewModel.desplegableExpandido = false
                                })
                        }
                    }
                }

            }

            Row(modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { viewModel.tirarRangos() }
                .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                for (numeroValor in 0 until viewModel.numeroRangos) {
                    val valorRango = viewModel.valoresRangos[numeroValor]
                    Text(
                        text = if (valorRango == null) "" else "$valorRango",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    for (numero in 1..3) {
                        FilterChip(
                            selected = viewModel.numeroRangos == numero,
                            onClick = { viewModel.numeroRangos = numero },
                            label = {
                                Text(text = "$numero")
                            },
                            shape = when (numero) {
                                1 -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                                3 -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                                else -> CutCornerShape(0.dp)
                            }
                        )
                    }
                }

                Row {
                    OutlinedIconButton(
                        onClick = { viewModel.reiniciarRangos() },
                        enabled = viewModel.valoresRangosModificados()
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

                    FilledIconButton(
                        onClick = { viewModel.tirarRangos() }, enabled = viewModel.rangoDefinido()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Pin,
                            contentDescription = stringResource(id = R.string.tirar_rango)
                        )
                    }
                }
            }
        }
    }
}