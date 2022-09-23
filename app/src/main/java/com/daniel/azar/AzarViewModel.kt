package com.daniel.azar

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

open class AzarViewModel : ViewModel() {
    val valoresIniciales = listOf<Int?>(null, null, null)

    fun numerosAleatorios(inicio: Int = 1, final: Int): List<Int> {
        val semilla = SimpleDateFormat("HHmmssSSS", Locale.FRENCH).format(Date()).toInt()
        val generador = Random(semilla)
        return List(3) { generador.nextInt(inicio, final + 1) }
    }
}