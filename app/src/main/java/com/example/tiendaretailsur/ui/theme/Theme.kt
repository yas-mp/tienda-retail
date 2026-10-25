package com.example.tiendaretailsur.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val EsquemaOscuro = darkColorScheme(
    primary = CardOscura,
    onPrimary = TextoClaro,
    background = FondoAppOscuro,
    onBackground = TextoOscuro,
    surface = CardOscura,
    onSurface = TextoClaro,
    surfaceVariant = CardOscura,
    onSurfaceVariant = TextoOscuro,
    primaryContainer = ExitoFondo,
    onPrimaryContainer = TextoClaro,
    error = ErrorColor,
    outlineVariant = GrisDeshabilitada
)

private val EsquemaClaro = lightColorScheme(
    primary = FondoApp,
    onPrimary = Color.White,
    background = FondoAppClaro,
    onBackground = Color(0xFF1C2B2B),
    surface = CardClara,
    onSurface = Color(0xFF1C2B2B),
    surfaceVariant = FondoApp,
    onSurfaceVariant = Color(0xFF5E6E6E),
    primaryContainer = FondoAppClaro,
    onPrimaryContainer = Color(0xFF1C2B2B),
    error = ErrorColor,
    outlineVariant = GrisDeshabilitada
)
private val Formas = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(20.dp)
)

@Composable
fun MiRegistroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Tipografia,
        shapes = Formas,
        content = content
    )
}