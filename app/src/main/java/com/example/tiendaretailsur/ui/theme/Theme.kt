package com.example.tiendaretailsur.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val EsquemaClaro = lightColorScheme(
    background = FondoAppClaro,
    onBackground = TextoClaro,
    surface = CardClara,
    onSurface = TextoClaro,
    surfaceVariant = CardClara,
    onSurfaceVariant = TextoClaro,
    primary = FondoApp,
    onPrimary = CardClara,
    secondary = FondoApp,
    onSecondary = CardClara,
    primaryContainer = CardClara,
    onPrimaryContainer = TextoClaro
)

private val EsquemaOscuro = darkColorScheme(
    background = FondoAppOscuro,
    onBackground = TextoOscuro,
    surface = CardOscura,
    onSurface = TextoOscuro,
    surfaceVariant = CardOscura,
    onSurfaceVariant = TextoOscuro,
    primary = TextoOscuro,
    onPrimary = FondoAppOscuro,
    secondary = CardOscura,
    onSecondary = TextoOscuro,
    primaryContainer = CardOscura,
    onPrimaryContainer = TextoOscuro
)

@Composable
fun RinconSur(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Tipografia,
        content = content
    )
}