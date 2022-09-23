package com.daniel.azar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MonedaViewModel : AzarViewModel() {
    // Número de elementos elegidos
    var numeroMonedas by mutableStateOf(1)

    // Valores aleatorios obtenidos
    var valoresMonedas by mutableStateOf(valoresIniciales)

    // Rotación del dado y la moneda
    var gradosRotacion by mutableStateOf(0F)

    fun imagenMoneda(n: Int?): Int {
        return when (n) {
            1 -> R.drawable.cara
            2 -> R.drawable.cruz
            else -> R.drawable.moneda
        }
    }

    fun reiniciarMonedas() {
        valoresMonedas = valoresIniciales
    }

    fun valoresMonedasModificados(): Boolean {
        return valoresMonedas != valoresIniciales
    }

    fun tirarMonedas() {
        gradosRotacion -= 360f
        valoresMonedas = numerosAleatorios(final = 2)
    }
}