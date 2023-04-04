package com.daniel.azar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Looks3
import androidx.compose.material.icons.outlined.LooksOne
import androidx.compose.material.icons.outlined.LooksTwo
import androidx.compose.ui.graphics.vector.ImageVector

enum class Numero(
    val numero: Int,
    @StringRes val texto: Int,
    val icono: ImageVector
) {
    Uno(
        numero = 1,
        texto = R.string.numero_uno,
        icono = Icons.Outlined.LooksOne
    ),
    Dos(
        numero = 2,
        texto = R.string.numero_dos,
        icono = Icons.Outlined.LooksTwo
    ),
    Tres(
        numero = 3,
        texto = R.string.numero_tres,
        icono = Icons.Outlined.Looks3
    )
}