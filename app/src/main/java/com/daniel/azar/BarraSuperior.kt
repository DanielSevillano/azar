package com.daniel.azar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BarraSuperior(estadoPager: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(selectedTabIndex = estadoPager.currentPage) {
        Elemento.values().forEachIndexed { indice, elemento ->
            LeadingIconTab(
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
    }
}