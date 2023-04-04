package com.daniel.azar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.ui.graphics.vector.ImageVector

enum class Elemento(
    @StringRes val nombre: Int,
    val icono: ImageVector
) {
    Dado(
        nombre = R.string.elemento_dado,
        icono = Icons.Outlined.Casino
    ),
    Moneda(
        nombre = R.string.elemento_moneda,
        icono = Icons.Outlined.MonetizationOn
    ),
    Rango(
        nombre = R.string.elemento_rango,
        icono = Icons.Outlined.Pin
    )
}