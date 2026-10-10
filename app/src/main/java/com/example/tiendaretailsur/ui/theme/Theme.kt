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
    primary = Primario,
    onPrimary = Blanco,
    background = FondoAppOscuro,
    onBackground = TextoOscuro,
    surface = CardOscura,
    onSurface = TextoOscuro,
    surfaceVariant = CardOscura
)
private val EsquemaClaro = lightColorScheme(
    primary = Primario,
    onPrimary = Blanco,
    background = FondoApp,
    onBackground = CardClara,
    surface = CardClara,
    onSurface = TextoClaro,
    surfaceVariant = FondoAppClaro
)
private val Formas = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(20.dp)
)
@Composable
fun MiRegistroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
){
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }

    MaterialTheme(
        colorScheme,
        typography = Tipografia,
        shapes = Formas,
        content = content
    )
}