package com.example.tiendaretailsur.ui.styles

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.tiendaretailsur.ui.theme.Dimens

object EstilosTarjeta {
    val forma = RoundedCornerShape(Dimens.radioCampo)

    @Composable
    fun colores() : CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    )

    @Composable
    fun elevacion() : CardElevation = CardDefaults.cardElevation(
        defaultElevation = Dimens.elevacionBoton
    )
}