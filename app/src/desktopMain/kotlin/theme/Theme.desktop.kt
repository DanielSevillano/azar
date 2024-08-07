package theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getColorScheme(darkTheme: Boolean): ColorScheme {
    val colorScheme = if (darkTheme) darkScheme else lightScheme
    return colorScheme
}