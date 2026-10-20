package com.example.tiendaretailsur.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiendaretailsur.ui.theme.FondoAppClaro
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme

@Composable
fun TituloScreen(
    titulo : String,
    subtitulo : String? = null,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        if(subtitulo!=null){
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
        }
    }
}

@Preview
@Composable
fun TituloScreenPreview(){
    MiRegistroTheme {
        Box(
            modifier = Modifier
                .background(FondoAppClaro)
                .padding(16.dp)
        ){
            TituloScreen(
                titulo = "Crea tu cuenta",
                subtitulo = "Todo listo para llevar el control de tu espacio, desde el primer acceso."
            )
        }

    }
}