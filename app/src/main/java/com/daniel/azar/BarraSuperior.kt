package com.daniel.azar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BarraSuperior(
    estadoPager: PagerState,
    usarIconos: Boolean
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(selectedTabIndex = estadoPager.currentPage) {
        if (usarIconos) {
            Elemento.values().forEachIndexed { indice, elemento ->
                Tab(
                    selected = indice == estadoPager.currentPage,
                    onClick = { coroutineScope.launch { estadoPager.animateScrollToPage(indice) } },
                    text = { Text(text = stringResource(id = elemento.nombre)) },
                    icon = {
                        Icon(
                            imageVector = elemento.icono,
                            contentDescription = stringResource(id = elemento.nombre)
                        )
                    }
                )
            }
        } else {
            Elemento.values().forEachIndexed { indice, elemento ->
                Tab(
                    selected = indice == estadoPager.currentPage,
                    onClick = { coroutineScope.launch { estadoPager.animateScrollToPage(indice) } },
                    text = { Text(text = stringResource(id = elemento.nombre)) }
                )
            }
        }

    }
}