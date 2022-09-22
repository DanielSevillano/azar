package com.daniel.azar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class AzarViewModel : ViewModel() {
    private val valoresIniciales = listOf<Int?>(null, null, null)

    // Número de elementos elegidos
    var numeroDados by mutableStateOf(1)
    var numeroMonedas by mutableStateOf(1)
    var numeroRangos by mutableStateOf(1)

    // Valores aleatorios obtenidos
    var valoresDados by mutableStateOf(valoresIniciales)
    var valoresMonedas by mutableStateOf(valoresIniciales)
    var valoresRangos by mutableStateOf(valoresIniciales)

    // Rotación del dado y la moneda
    var gradosRotacion by mutableStateOf(0F)

    // Texto del rango
    var textoInicio by mutableStateOf("")
    var textoFinal by mutableStateOf("")

    // Desplegable del rango
    var desplegableExpandido by mutableStateOf(false)
    val opcionesDesplegable = listOf(
        listOf(1, 10), listOf(1, 100)
    )

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

    fun imagenMoneda(n: Int?): Int {
        return when (n) {
            1 -> R.drawable.cara
            2 -> R.drawable.cruz
            else -> R.drawable.moneda
        }
    }

    fun reiniciarDados() {
        valoresDados = valoresIniciales
    }

    fun reiniciarMonedas() {
        valoresMonedas = valoresIniciales
    }

    fun reiniciarRangos() {
        valoresRangos = valoresIniciales
    }

    fun reiniciarTextos() {
        textoInicio = ""
        textoFinal = ""
    }

    fun valoresDadosModificados(): Boolean {
        return valoresDados != valoresIniciales
    }

    fun valoresMonedasModificados(): Boolean {
        return valoresMonedas != valoresIniciales
    }

    fun valoresRangosModificados(): Boolean {
        return valoresRangos != valoresIniciales
    }

    fun rangoDefinido(): Boolean {
        return textoInicio.isNotBlank() and textoFinal.isNotBlank()
    }

    private fun numerosAleatorios(inicio: Int = 1, final: Int): List<Int> {
        val semilla = SimpleDateFormat("HHmmssSSS", Locale.FRENCH).format(Date()).toInt()
        val generador = Random(semilla)
        return List(3) { generador.nextInt(inicio, final + 1) }
    }

    fun tirarDados() {
        gradosRotacion += 360f
        valoresDados = numerosAleatorios(final = 6)
    }

    fun tirarMonedas() {
        gradosRotacion -= 360f
        valoresMonedas = numerosAleatorios(final = 2)
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