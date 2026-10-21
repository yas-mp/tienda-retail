package com.example.tiendaretailsur.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiendaretailsur.ui.theme.CardClara
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.FondoAppClaro
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme

@Composable
fun CasillaConTexto(
    texto : String,
    marcado : Boolean,
    onCambio : (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(marcado, onValueChange = onCambio, role = Role.Checkbox)
            .padding(Dimens.espacioPequeno),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(marcado, onCheckedChange = null, colors = CheckboxDefaults.colors(
            checkedColor = CardClara, checkmarkColor = MaterialTheme.colorScheme.onSurface, uncheckedColor = MaterialTheme.colorScheme.onSurface) )
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = Dimens.espacioPequeno),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ControlesSeleccionPreview(){
    MiRegistroTheme {
        Box(
            modifier = Modifier
                .background(FondoAppClaro)
                .padding(10.dp)
        ) {
            CasillaConTexto(
                texto = "Acepto los Términos de uso y Política de privacidad.",
                marcado = false ,
                onCambio = {}
            )
        }
    }

}