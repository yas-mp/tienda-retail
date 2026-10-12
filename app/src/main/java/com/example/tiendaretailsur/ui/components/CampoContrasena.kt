package com.example.tiendaretailsur.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.ui.styles.EstiloCampo
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.FondoAppClaro
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme

@Composable
fun CampoContrasena(
    valor: String,
    onValorCambia: (String) -> Unit,
    etiqueta: String,
    visible : Boolean,
    onAlternarContrasenaVisible: () -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null
){
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambia,
        modifier = modifier.fillMaxWidth(),
        label = { Text(etiqueta)},
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_candado),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
        },
        trailingIcon = {
            IconButton(onClick = onAlternarContrasenaVisible) {
                Icon(
                    painter = painterResource(
                        if(visible) R.drawable.ic_ojo_tachado else R.drawable.ic_ojo
                    ),
                    contentDescription = stringResource(
                        if(visible) R.string.cd_ocultar_contrasena else R.string.cd_mostrar_contrasena
                    ),
                    modifier = Modifier.size(Dimens.tamanoIcono)
                )
            }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = error!= null,
        supportingText = { if(error != null) Text(error)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = EstiloCampo.forma,
        colors = EstiloCampo.colores()
    )
}

@Preview(showBackground = true)
@Composable
private fun CampoContrasenaPreview(){
    MiRegistroTheme{
        Box(
            modifier = Modifier
                .background(FondoAppClaro)
                .padding(16.dp)
        ){
            CampoContrasena(
                valor = "secreto",
                onValorCambia = {},
                etiqueta = "Contrasena",
                visible = false,
                onAlternarContrasenaVisible = {}
            )
        }
    }
}