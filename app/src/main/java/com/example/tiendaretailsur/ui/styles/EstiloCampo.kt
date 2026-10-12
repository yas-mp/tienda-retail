package com.example.tiendaretailsur.ui.styles

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.example.tiendaretailsur.ui.theme.Blanco
import com.example.tiendaretailsur.ui.theme.CardClara
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.Error
import com.example.tiendaretailsur.ui.theme.GrisDeshabilitada
import com.example.tiendaretailsur.ui.theme.TextoClaro


object EstiloCampo {
    val forma = RoundedCornerShape(Dimens.radioCampo)

    @Composable
    fun colores() : TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = CardClara,
        unfocusedContainerColor = CardClara,
        errorContainerColor = CardClara,

        focusedBorderColor = Blanco,
        unfocusedBorderColor = GrisDeshabilitada,
        errorBorderColor = Error,

        focusedLeadingIconColor = TextoClaro,
        unfocusedLeadingIconColor = TextoClaro,
        errorLeadingIconColor = Error,

        focusedTextColor = TextoClaro,
        unfocusedTextColor = TextoClaro,
        errorTextColor = TextoClaro,

        focusedPlaceholderColor = TextoClaro.copy(0.7f),
        unfocusedPlaceholderColor = TextoClaro.copy(0.7f),
        focusedLabelColor = Blanco,
        unfocusedLabelColor = Blanco,
        cursorColor = Blanco,

        focusedTrailingIconColor = Blanco


    )
}