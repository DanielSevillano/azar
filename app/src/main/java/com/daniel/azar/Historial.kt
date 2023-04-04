package com.daniel.azar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Historial(
    cerrarHistorial: () -> Unit,
    estadoHistorial: SheetState,
    elementoSeleccionado: Elemento,
    tiradasElemento: List<Tirada>,
    representarTirada: (Int) -> Int,
    eliminarHistorial: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { cerrarHistorial() },
        sheetState = estadoHistorial,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = stringResource(id = R.string.historial)
                        )

                        Text(
                            text = stringResource(id = R.string.historial),
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { eliminarHistorial() }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(id = R.string.historial_eliminar)
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 12.dp)
                            .alpha(0.2F)
                    )
                }
            }

            if (tiradasElemento.isEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.historial_vacio),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(tiradasElemento.reversed()) { tirada ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tirada.valoresObtenidos.forEach {  valor ->
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
                            else -> {
                                Image(
                                    painter = painterResource(id = representarTirada(valor)),
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