package com.daniel.azar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AzarViewModel : ViewModel() {
    var numeroElementos by mutableStateOf(Numero.Uno)

    var valoresDado: List<Int>? by mutableStateOf(null)
    var valoresMoneda: List<Int>? by mutableStateOf(null)
    var valoresRango: List<Int>? by mutableStateOf(null)

    private var tiradasDado by mutableStateOf(emptyList<Tirada>())
    private var tiradasMoneda by mutableStateOf(emptyList<Tirada>())
    private var tiradasRango by mutableStateOf(emptyList<Tirada>())

    var inicioRango by mutableStateOf(1)
    var finalRango by mutableStateOf(10)

    var gradosRotacion by mutableStateOf(0F)

    fun tirarElemento(elemento: Elemento) {
        gradosRotacion += 360f

        when (elemento) {
            Elemento.Dado -> {
                valoresDado = List(size = numeroElementos.numero) { (1..6).random() }
                tiradasDado = tiradasDado + Tirada(valoresDado!!)
            }
            Elemento.Moneda -> {
                valoresMoneda = List(size = numeroElementos.numero) { (1..2).random() }
                tiradasMoneda = tiradasMoneda + Tirada(valoresMoneda!!)
            }
            Elemento.Rango -> {
                valoresRango =
                    List(size = numeroElementos.numero) { (inicioRango..finalRango).random() }
                tiradasRango = tiradasRango + Tirada(valoresRango!!)
            }
        }
    }

    fun valoresElemento(elemento: Elemento): List<Int>? {
        return when (elemento) {
            Elemento.Dado -> valoresDado
            Elemento.Moneda -> valoresMoneda
            Elemento.Rango -> valoresRango
        }
    }

    fun tiradasElemento(elemento: Elemento): List<Tirada> {
        return when (elemento) {
            Elemento.Dado -> tiradasDado
            Elemento.Moneda -> tiradasMoneda
            Elemento.Rango -> tiradasRango
        }
    }

    fun representarDado(numero: Int): Int {
        return when (numero) {
            1 -> R.drawable.dado_1
            2 -> R.drawable.dado_2
            3 -> R.drawable.dado_3
            4 -> R.drawable.dado_4
            5 -> R.drawable.dado_5
            6 -> R.drawable.dado_6
            else -> R.drawable.dado_0
        }
    }

    fun representarMoneda(numero: Int): Int {
        return when (numero) {
            1 -> R.drawable.cara
            2 -> R.drawable.cruz
            else -> R.drawable.moneda
        }
    }

    fun representarTirada(elemento: Elemento): (Int) -> Int {
        return when (elemento) {
            Elemento.Dado -> { n -> representarDado(n) }
            Elemento.Moneda -> { n -> representarMoneda(n) }
            else -> { _ -> 0 }
        }
    }

    fun cambiarRango(inicio: Int, final: Int) {
        inicioRango = inicio
        finalRango = final
    }

    fun eliminarHistorialElemento(elemento: Elemento) {
        when (elemento) {
            Elemento.Dado -> tiradasDado = emptyList()
            Elemento.Moneda -> tiradasMoneda = emptyList()
            Elemento.Rango -> tiradasRango = emptyList()
        }
    }

    fun borrarValoresElemento(elemento: Elemento) {
        when (elemento) {
            Elemento.Dado -> valoresDado = null
            Elemento.Moneda -> valoresMoneda = null
            Elemento.Rango -> valoresRango = null
        }
    }
}