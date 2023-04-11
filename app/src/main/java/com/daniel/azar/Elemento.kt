package com.daniel.azar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
    ),
    Letra(
        nombre = R.string.elemento_letra,
        icono = Icons.Outlined.FontDownload
    ),
    Color(
        nombre = R.string.elemento_color,
        icono = Icons.Outlined.Palette
    )
}