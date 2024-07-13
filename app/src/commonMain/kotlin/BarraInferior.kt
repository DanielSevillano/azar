import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import azar.app.generated.resources.Res
import azar.app.generated.resources.borrar
import azar.app.generated.resources.historial
import azar.app.generated.resources.tirar_color
import azar.app.generated.resources.tirar_dado
import azar.app.generated.resources.tirar_letra
import azar.app.generated.resources.tirar_moneda
import azar.app.generated.resources.tirar_rango
import org.jetbrains.compose.resources.stringResource

@Composable
fun BarraInferior(
    elementoSeleccionado: Elemento,
    numeroSeleccionado: Numero,
    tirarElemento: (Elemento) -> Unit,
    cambiarNumeroSeleccionado: (Numero) -> Unit,
    abrirHistorial: () -> Unit,
    valoresElemento: List<Int>?,
    borrarValores: (Elemento) -> Unit
) {
    BottomAppBar(
        actions = {
            IconButton(onClick = {
                cambiarNumeroSeleccionado(
                    when (numeroSeleccionado) {
                        Numero.Uno -> Numero.Dos
                        Numero.Dos -> Numero.Tres
                        Numero.Tres -> Numero.Uno
                    }
                )
            }) {
                Icon(
                    imageVector = numeroSeleccionado.icono,
                    contentDescription = stringResource(resource = numeroSeleccionado.texto)
                )
            }

            IconButton(onClick = { abrirHistorial() }) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = stringResource(resource = Res.string.historial)
                )
            }

            AnimatedVisibility(visible = !valoresElemento.isNullOrEmpty()) {
                IconButton(onClick = { borrarValores(elementoSeleccionado) }) {
                    Icon(
                        imageVector = Icons.Outlined.ClearAll,
                        contentDescription = stringResource(resource = Res.string.borrar)
                    )
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(
                            resource = when (elementoSeleccionado) {
                                Elemento.Dado -> Res.string.tirar_dado
                                Elemento.Moneda -> Res.string.tirar_moneda
                                Elemento.Rango -> Res.string.tirar_rango
                                Elemento.Letra -> Res.string.tirar_letra
                                Elemento.Color -> Res.string.tirar_color
                            }
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = when (elementoSeleccionado) {
                            Elemento.Dado -> stringResource(resource = Res.string.tirar_dado)
                            Elemento.Moneda -> stringResource(resource = Res.string.tirar_moneda)
                            Elemento.Rango -> stringResource(resource = Res.string.tirar_rango)
                            Elemento.Letra -> stringResource(resource = Res.string.tirar_letra)
                            Elemento.Color -> stringResource(resource = Res.string.tirar_color)
                        }
                    )
                },
                onClick = { tirarElemento(elementoSeleccionado) },
                modifier = Modifier.animateContentSize(),
                expanded = valoresElemento.isNullOrEmpty(),
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            )
        }
    )
}