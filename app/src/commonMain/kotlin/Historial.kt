import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import azar.app.generated.resources.Res
import azar.app.generated.resources.historial
import azar.app.generated.resources.historial_eliminar
import azar.app.generated.resources.historial_vacio
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun Historial(
    cerrarHistorial: () -> Unit,
    estadoHistorial: SheetState,
    elementoSeleccionado: Elemento,
    tiradasElemento: List<Tirada>,
    representarTirada: (Int) -> DrawableResource,
    eliminarHistorial: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { cerrarHistorial() },
        sheetState = estadoHistorial,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                Surface {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.History,
                                contentDescription = stringResource(resource = Res.string.historial)
                            )

                            Text(
                                text = stringResource(resource = Res.string.historial),
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            AnimatedVisibility(visible = tiradasElemento.isNotEmpty()) {
                                FilledTonalIconButton(onClick = { eliminarHistorial() }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = stringResource(resource = Res.string.historial_eliminar)
                                    )
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .alpha(0.2F)
                        )
                    }
                }
            }

            if (tiradasElemento.isEmpty()) {
                item {
                    Text(
                        text = stringResource(resource = Res.string.historial_vacio),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(tiradasElemento.reversed()) { tirada ->
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tirada.valoresObtenidos.forEach { valor ->
                        when (elementoSeleccionado) {
                            Elemento.Rango -> {
                                Text(
                                    text = " $valor ",
                                    modifier = Modifier
                                        .widthIn(min = 40.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }

                            Elemento.Letra -> {
                                Text(
                                    text = " ${valor.toChar()} ",
                                    modifier = Modifier
                                        .widthIn(min = 40.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }

                            Elemento.Color -> {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(Color(color = valor + 4278190080))
                                )
                            }

                            else -> {
                                Image(
                                    painter = painterResource(resource = representarTirada(valor)),
                                    contentDescription = "$valor",
                                    modifier = Modifier.widthIn(max = 60.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}