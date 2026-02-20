import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import azar.composeapp.generated.resources.Res
import azar.composeapp.generated.resources.cara
import azar.composeapp.generated.resources.cruz
import azar.composeapp.generated.resources.dado_0
import azar.composeapp.generated.resources.dado_1
import azar.composeapp.generated.resources.dado_2
import azar.composeapp.generated.resources.dado_3
import azar.composeapp.generated.resources.dado_4
import azar.composeapp.generated.resources.dado_5
import azar.composeapp.generated.resources.dado_6
import azar.composeapp.generated.resources.moneda
import org.jetbrains.compose.resources.DrawableResource

class AzarViewModel : ViewModel() {
    var numeroElementos by mutableIntStateOf(1)

    var valoresDado: List<Int>? by mutableStateOf(null)
    var valoresMoneda: List<Int>? by mutableStateOf(null)
    var valoresRango: List<Int>? by mutableStateOf(null)
    var valoresLetra: List<Int>? by mutableStateOf(null)
    var valoresColor: List<Int>? by mutableStateOf(null)

    private var tiradasDado by mutableStateOf(emptyList<Tirada>())
    private var tiradasMoneda by mutableStateOf(emptyList<Tirada>())
    private var tiradasRango by mutableStateOf(emptyList<Tirada>())
    private var tiradasLetra by mutableStateOf(emptyList<Tirada>())
    private var tiradasColor by mutableStateOf(emptyList<Tirada>())

    var inicioRango by mutableIntStateOf(1)
    var finalRango by mutableIntStateOf(10)

    var gradosRotacion by mutableFloatStateOf(0F)

    fun tirarElemento(elemento: Elemento) {
        gradosRotacion += 360f

        when (elemento) {
            Elemento.Dado -> {
                valoresDado = List(size = numeroElementos) { (1..6).random() }
                tiradasDado = tiradasDado + Tirada(valoresDado!!)
            }

            Elemento.Moneda -> {
                valoresMoneda = List(size = numeroElementos) { (1..2).random() }
                tiradasMoneda = tiradasMoneda + Tirada(valoresMoneda!!)
            }

            Elemento.Rango -> {
                valoresRango =
                    List(size = numeroElementos) { (inicioRango..finalRango).random() }
                tiradasRango = tiradasRango + Tirada(valoresRango!!)
            }

            Elemento.Letra -> {
                valoresLetra = List(size = numeroElementos) { ('A'..'Z').random().code }
                tiradasLetra = tiradasLetra + Tirada(valoresLetra!!)
            }

            Elemento.Color -> {
                valoresColor = List(size = numeroElementos) { (0..16777215).random() }
                tiradasColor = tiradasColor + Tirada(valoresColor!!)
            }
        }
    }

    fun valoresElemento(elemento: Elemento): List<Int>? {
        return when (elemento) {
            Elemento.Dado -> valoresDado
            Elemento.Moneda -> valoresMoneda
            Elemento.Rango -> valoresRango
            Elemento.Letra -> valoresLetra
            Elemento.Color -> valoresColor
        }
    }

    fun tiradasElemento(elemento: Elemento): List<Tirada> {
        return when (elemento) {
            Elemento.Dado -> tiradasDado
            Elemento.Moneda -> tiradasMoneda
            Elemento.Rango -> tiradasRango
            Elemento.Letra -> tiradasLetra
            Elemento.Color -> tiradasColor
        }
    }

    fun representarDado(numero: Int): DrawableResource {
        return when (numero) {
            1 -> Res.drawable.dado_1
            2 -> Res.drawable.dado_2
            3 -> Res.drawable.dado_3
            4 -> Res.drawable.dado_4
            5 -> Res.drawable.dado_5
            6 -> Res.drawable.dado_6
            else -> Res.drawable.dado_0
        }
    }

    fun representarMoneda(numero: Int): DrawableResource {
        return when (numero) {
            1 -> Res.drawable.cara
            2 -> Res.drawable.cruz
            else -> Res.drawable.moneda
        }
    }

    fun representarTirada(elemento: Elemento): (Int) -> DrawableResource {
        return when (elemento) {
            Elemento.Dado -> { n -> representarDado(n) }
            Elemento.Moneda -> { n -> representarMoneda(n) }
            else -> { _ -> Res.drawable.dado_0 }
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
            Elemento.Letra -> tiradasLetra = emptyList()
            Elemento.Color -> tiradasColor = emptyList()
        }
    }

    fun borrarValoresElemento(elemento: Elemento) {
        when (elemento) {
            Elemento.Dado -> valoresDado = null
            Elemento.Moneda -> valoresMoneda = null
            Elemento.Rango -> valoresRango = null
            Elemento.Letra -> valoresLetra = null
            Elemento.Color -> valoresColor = null
        }
    }
}