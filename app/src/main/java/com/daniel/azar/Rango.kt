package com.daniel.azar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Rango() {
    val valoresIniciales = listOf<Int?>(null, null, null)

    var numeroValores: Int by remember { mutableStateOf(value = 1) }
    var valoresRango: List<Int?> by remember { mutableStateOf(valoresIniciales) }

    var textoInicio by remember { mutableStateOf("") }
    var textoFinal by remember { mutableStateOf("") }
    val rangoDefinido =
        remember(textoInicio, textoFinal) { textoInicio.isNotBlank() and textoFinal.isNotBlank() }

    fun tirarRango(valorInicial: Int, valorFinal: Int): List<Int> {
        val inicio = min(valorInicial, valorFinal)
        val final = max(valorInicial, valorFinal)
        return numerosAleatorios(inicio, final)
    }

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
                value = textoInicio,
                onValueChange = {
                    if ((it.toIntOrNull() != null) or it.isBlank()) textoInicio = it
                },
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
                value = textoFinal,
                onValueChange = { if ((it.toIntOrNull() != null) or it.isBlank()) textoFinal = it },
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
                onClick = {
                    textoInicio = ""
                    textoFinal = ""
                },
                enabled = textoInicio.isNotBlank() or textoFinal.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }

            Column {
                var desplegableExpandido by remember { mutableStateOf(false) }

                FilledIconButton(onClick = { desplegableExpandido = true }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.desplegar)
                    )
                }

                DropdownMenu(
                    expanded = desplegableExpandido,
                    onDismissRequest = { desplegableExpandido = false }) {
                    DropdownMenuItem(text = { Text(text = "1 — 10") }, onClick = {
                        textoInicio = "1"
                        textoFinal = "10"
                        desplegableExpandido = false
                    })
                    DropdownMenuItem(text = { Text(text = "1 — 100") }, onClick = {
                        textoInicio = "1"
                        textoFinal = "100"
                        desplegableExpandido = false
                    })
                }
            }

        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    if (rangoDefinido) {
                        val valorInicial = textoInicio.toInt()
                        val valorFinal = textoFinal.toInt()
                        valoresRango = tirarRango(valorInicial, valorFinal)
                    }
                }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            for (numeroValor in 0 until numeroValores) {
                val valorRango = valoresRango[numeroValor]
                Text(
                    text = if (valorRango == null) "" else "$valorRango",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FilledIconButton(
                onClick = {
                    val valorInicial = textoInicio.toInt()
                    val valorFinal = textoFinal.toInt()
                    valoresRango = tirarRango(valorInicial, valorFinal)
                },
                enabled = rangoDefinido
            ) {
                Icon(
                    imageVector = Icons.Filled.Pin,
                    contentDescription = stringResource(id = R.string.tirar_rango)
                )
            }

            Row {
                for (numero in 1..3) {
                    FilterChip(
                        selected = numeroValores == numero,
                        onClick = { numeroValores = numero },
                        label = {
                            Text(text = "$numero")
                        })
                }
            }

            FilledTonalIconButton(
                onClick = { valoresRango = valoresIniciales },
                enabled = valoresRango != valoresIniciales
            ) {
                Icon(
                    imageVector = Icons.Filled.ClearAll,
                    contentDescription = stringResource(id = R.string.borrar)
                )
            }
        }
    }
}