package com.daniel.azar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destino(
    val ruta: String,
    @StringRes val nombreId: Int,
    val icono: ImageVector,
    val iconoSeleccionado: ImageVector
) {
    Dado(
        "dado",
        R.string.destino_dado,
        Icons.Outlined.Casino,
        Icons.Filled.Casino
    ),
    Moneda(
        "moneda",
        R.string.destino_moneda,
        Icons.Outlined.MonetizationOn,
        Icons.Filled.MonetizationOn
    ),
    Rango(
        "rango",
        R.string.destino_rango,
        Icons.Outlined.Pin,
        Icons.Filled.Pin
    )
}