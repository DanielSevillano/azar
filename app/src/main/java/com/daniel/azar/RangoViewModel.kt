package com.daniel.azar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.max
import kotlin.math.min

class RangoViewModel : AzarViewModel() {
    // Número de elementos elegidos
    var numeroRangos by mutableStateOf(1)

    // Valores aleatorios obtenidos
    var valoresRangos by mutableStateOf(valoresIniciales)

    // Texto del rango
    var textoInicio by mutableStateOf("")
    var textoFinal by mutableStateOf("")

    // Desplegable del rango
    var desplegableExpandido by mutableStateOf(false)
    val opcionesDesplegable = listOf(
        listOf(1, 10), listOf(1, 100)
    )

    fun reiniciarRangos() {
        valoresRangos = valoresIniciales
    }

    fun reiniciarTextos() {
        textoInicio = ""
        textoFinal = ""
    }

    fun valoresRangosModificados(): Boolean {
        return valoresRangos != valoresIniciales
    }

    fun rangoDefinido(): Boolean {
        return textoInicio.isNotBlank() and textoFinal.isNotBlank()
    }

    fun tirarRangos() {
        try {
            val valorInicial = textoInicio.toInt()
            val valorFinal = textoFinal.toInt()
            val inicio = min(valorInicial, valorFinal)
            val final = max(valorInicial, valorFinal)
            valoresRangos = numerosAleatorios(inicio, final)
        } catch (_: Exception) {
        }
    }
}