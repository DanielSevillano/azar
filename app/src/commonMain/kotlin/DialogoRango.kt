import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsEthernet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import azar.app.generated.resources.Res
import azar.app.generated.resources.elemento_rango
import azar.app.generated.resources.rango_cancelar
import azar.app.generated.resources.rango_confirmar
import azar.app.generated.resources.rango_definir
import azar.app.generated.resources.rango_final
import azar.app.generated.resources.rango_inicio
import org.jetbrains.compose.resources.stringResource
import kotlin.math.max
import kotlin.math.min

@Composable
fun DialogoRango(
    cerrarDialogo: () -> Unit,
    inicioRango: Int,
    finalRango: Int,
    cambiarRango: (Int, Int) -> Unit,
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

                    cambiarRango(min(numero1, numero2), max(numero1, numero2))
                    cerrarDialogo()
                },
                enabled = textoInicioRango.isNotBlank() and textoFinalRango.isNotBlank()
            ) {
                Text(text = stringResource(resource = Res.string.rango_confirmar))
            }
        },
        dismissButton = {
            TextButton(onClick = { cerrarDialogo() }) {
                Text(text = stringResource(resource = Res.string.rango_cancelar))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.SettingsEthernet,
                contentDescription = stringResource(resource = Res.string.elemento_rango)
            )
        },
        title = { Text(text = stringResource(resource = Res.string.rango_definir)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = textoInicioRango,
                    onValueChange = { texto -> textoInicioRango = texto.filter { it.isDigit() } },
                    label = { Text(text = stringResource(resource = Res.string.rango_inicio)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                TextField(
                    value = textoFinalRango,
                    onValueChange = { texto -> textoFinalRango = texto.filter { it.isDigit() } },
                    label = { Text(text = stringResource(resource = Res.string.rango_final)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
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