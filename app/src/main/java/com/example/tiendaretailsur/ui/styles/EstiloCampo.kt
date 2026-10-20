package com.example.tiendaretailsur.ui.styles

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.example.tiendaretailsur.ui.theme.CardClara
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.ErrorColor
import com.example.tiendaretailsur.ui.theme.GrisDeshabilitada
import com.example.tiendaretailsur.ui.theme.TextoClaro


object EstiloCampo {
    val forma = RoundedCornerShape(Dimens.radioCampo)

    @Composable
    fun colores() : TextFieldColors = colors(
        focusedContainerColor = CardClara,
        unfocusedContainerColor = CardClara,
        errorContainerColor = CardClara,

        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
        unfocusedBorderColor = GrisDeshabilitada,
        errorBorderColor = ErrorColor,

        focusedLeadingIconColor = TextoClaro,
        unfocusedLeadingIconColor = TextoClaro,
        errorLeadingIconColor = ErrorColor,

        focusedTextColor = TextoClaro,
        unfocusedTextColor = TextoClaro,
        errorTextColor = TextoClaro,

        focusedPlaceholderColor = TextoClaro.copy(0.7f),
        unfocusedPlaceholderColor = TextoClaro.copy(0.7f),
        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
        cursorColor = MaterialTheme.colorScheme.onSurface,

        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface


    )
}