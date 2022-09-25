package com.daniel.azar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Historial(
    destino: Destino,
    tiradas: List<Tirada>,
    cerrarHistorial: () -> Unit,
    imagenValor: (Int?) -> Int = { R.drawable.dado_0 }
) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                        IconButton(onClick = { cerrarHistorial() }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Cerrar"
                            ) // Traducir
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 12.dp)
                            .alpha(0.2F)
                    )
                }
            }

            if (tiradas.isEmpty()) {
                item {
                    Text(
                        text = "No hay historial",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(tiradas.reversed()) { tirada ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (n in 0 until tirada.numeroElementos) {
                        val valor = tirada.valoresObtenidos[n]

                        if ((destino == Destino.Dado) or (destino == Destino.Moneda)) {
                            Image(
                                painter = painterResource(id = imagenValor(valor)),
                                contentDescription = "$valor",
                                modifier = Modifier.widthIn(max = 60.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Text(
                                text = "$valor",
                                modifier = Modifier.widthIn(min = 40.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}