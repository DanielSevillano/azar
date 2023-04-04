package com.daniel.azar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsEthernet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun DialogoRango(
    cerrarDialogo: () -> Unit,
    inicioRango: Int,
    finalRango: Int,
    cambiarInicioRango: (Int) -> Unit,
    cambiarFinalRango: (Int) -> Unit
) {
    var textoInicioRango by rememberSaveable { mutableStateOf("$inicioRango") }
    var textoFinalRango by rememberSaveable { mutableStateOf("$finalRango") }

    AlertDialog(
        onDismissRequest = { cerrarDialogo() },
        confirmButton = {
            TextButton(
                onClick = {
                    val numero1 = textoInicioRango.toInt()
                    val numero2 = textoFinalRango.toInt()

                    cambiarInicioRango(min(numero1, numero2))
                    cambiarFinalRango(max(numero1, numero2))
                    cerrarDialogo()
                },
                enabled = textoInicioRango.isNotBlank() and textoFinalRango.isNotBlank()
            ) {
                Text(text = stringResource(id = R.string.rango_confirmar))
            }
        },
        dismissButton = {
            TextButton(onClick = { cerrarDialogo() }) {
                Text(text = stringResource(id = R.string.rango_cancelar))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.SettingsEthernet,
                contentDescription = stringResource(id = R.string.elemento_rango)
            )
        },
        title = { Text(text = stringResource(id = R.string.rango_definir)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = textoInicioRango,
                    onValueChange = { texto -> textoInicioRango = texto.filter { it.isDigit() } },
                    label = { Text(text = stringResource(id = R.string.rango_inicio)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                TextField(
                    value = textoFinalRango,
                    onValueChange = { texto -> textoFinalRango = texto.filter { it.isDigit() } },
                    label = { Text(text = stringResource(id = R.string.rango_final)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AssistChip(
                        onClick = {
                            textoInicioRango = "1"
                            textoFinalRango = "10"
                        },
                        label = { Text(text = "1 - 10") }
                    )

                    AssistChip(
                        onClick = {
                            textoInicioRango = "1"
                            textoFinalRango = "100"
                        },
                        label = { Text(text = "1 - 100") }
                    )
                }
            }
        }
    )
}