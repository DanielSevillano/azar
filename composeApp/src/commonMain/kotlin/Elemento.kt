import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.ui.graphics.vector.ImageVector
import azar.composeapp.generated.resources.Res
import azar.composeapp.generated.resources.elemento_color
import azar.composeapp.generated.resources.elemento_dado
import azar.composeapp.generated.resources.elemento_letra
import azar.composeapp.generated.resources.elemento_moneda
import azar.composeapp.generated.resources.elemento_rango
import org.jetbrains.compose.resources.StringResource

enum class Elemento(
    val nombre: StringResource,
    val icono: ImageVector
) {
    Dado(
        nombre = Res.string.elemento_dado,
        icono = Icons.Outlined.Casino
    ),
    Moneda(
        nombre = Res.string.elemento_moneda,
        icono = Icons.Outlined.MonetizationOn
    ),
    Rango(
        nombre = Res.string.elemento_rango,
        icono = Icons.Outlined.Pin
    ),
    Letra(
        nombre = Res.string.elemento_letra,
        icono = Icons.Outlined.FontDownload
    ),
    Color(
        nombre = Res.string.elemento_color,
        icono = Icons.Outlined.Palette
    )
}