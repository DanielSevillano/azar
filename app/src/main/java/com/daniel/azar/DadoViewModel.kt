package com.daniel.azar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DadoViewModel : AzarViewModel() {
    // Número de elementos elegidos
    var numeroDados by mutableStateOf(1)

    // Valores aleatorios obtenidos
    var valoresDados by mutableStateOf(valoresIniciales)

    // Rotación del dado y la moneda
    var gradosRotacion by mutableStateOf(0F)

    fun imagenDado(n: Int?): Int {
        return when (n) {
            1 -> R.drawable.dado_1
            2 -> R.drawable.dado_2
            3 -> R.drawable.dado_3
            4 -> R.drawable.dado_4
            5 -> R.drawable.dado_5
            6 -> R.drawable.dado_6
            else -> R.drawable.dado_0
        }
    }

    fun reiniciarDados() {
        valoresDados = valoresIniciales
    }

    fun valoresDadosModificados(): Boolean {
        return valoresDados != valoresIniciales
    }

    fun tirarDados() {
        gradosRotacion += 360f
        valoresDados = numerosAleatorios(final = 6)
    }
}