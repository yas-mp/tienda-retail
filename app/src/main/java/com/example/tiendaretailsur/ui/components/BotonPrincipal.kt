package com.example.tiendaretailsur.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.ui.styles.EstiloBoton
import com.example.tiendaretailsur.ui.styles.estiloAltoBoton
import com.example.tiendaretailsur.ui.theme.FondoAppClaro
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme


@Composable

fun BotonPrincipal(
    texto : String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado : Boolean = true,
    @DrawableRes icono: Int? = null
){
    Button(
        onClick = onClick,
        modifier = modifier.estiloAltoBoton(),
        enabled = habilitado,
        shape = EstiloBoton.forma,
        colors = EstiloBoton.coloresPrincipal(),
        elevation = EstiloBoton.elevacion(),
        contentPadding = EstiloBoton.relleno
    ){
        if(icono!=null){
            Icon(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text = texto)
    }
}

@Preview(showBackground = true)
@Composable
private fun BotonPrincipalPreview(){
    MiRegistroTheme {
        Box(
            modifier = Modifier
                .background(FondoAppClaro)
                .padding(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                BotonPrincipal(
                    texto = "Registrarme",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.size(8.dp))
                BotonPrincipal(
                    texto = "Con ícono",
                    onClick = {},
                    icono = R.drawable.ic_check,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.size(8.dp))
                BotonPrincipal(
                    texto = "Deshabilitado",
                    onClick = {},
                    habilitado = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}