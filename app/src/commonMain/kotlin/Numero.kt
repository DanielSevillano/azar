import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Looks3
import androidx.compose.material.icons.outlined.LooksOne
import androidx.compose.material.icons.outlined.LooksTwo
import androidx.compose.ui.graphics.vector.ImageVector
import azar.app.generated.resources.Res
import azar.app.generated.resources.numero_dos
import azar.app.generated.resources.numero_tres
import azar.app.generated.resources.numero_uno
import org.jetbrains.compose.resources.StringResource

enum class Numero(
    val numero: Int,
    val texto: StringResource,
    val icono: ImageVector
) {
    Uno(
        numero = 1,
        texto = Res.string.numero_uno,
        icono = Icons.Outlined.LooksOne
    ),
    Dos(
        numero = 2,
        texto = Res.string.numero_dos,
        icono = Icons.Outlined.LooksTwo
    ),
    Tres(
        numero = 3,
        texto = Res.string.numero_tres,
        icono = Icons.Outlined.Looks3
    )
}