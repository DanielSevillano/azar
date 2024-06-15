package com.daniel.azar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(estadoPager: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    PrimaryScrollableTabRow(
        selectedTabIndex = estadoPager.currentPage,
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Elemento.entries.forEachIndexed { indice, elemento ->
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