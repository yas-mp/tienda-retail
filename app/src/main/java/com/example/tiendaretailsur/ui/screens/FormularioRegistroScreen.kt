package com.example.tiendaretailsur.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.model.FormularioRegistroUiState
import com.example.tiendaretailsur.ui.components.BotonPrincipal
import com.example.tiendaretailsur.ui.components.CampoContrasena
import com.example.tiendaretailsur.ui.components.CampoTexto
import com.example.tiendaretailsur.ui.components.CampoTextoRut
import com.example.tiendaretailsur.ui.components.CasillaConTexto
import com.example.tiendaretailsur.ui.components.TituloScreen
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.Exito
import com.example.tiendaretailsur.ui.theme.ExitoFondo
import com.example.tiendaretailsur.viewmodel.FormularioRegistroViewModel

@Composable
fun FormularioRegistroScreen(
    viewModel: FormularioRegistroViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FormularioRegistroContent(
        uiState = uiState,
        onRutCambia = viewModel::onRutCambia,
        onNombreCambia = viewModel::onNombreCambia,
        onCorreoCambia = viewModel::onCorreoCambia,
        onTelefonoCambia = viewModel::onTelefonoCambia,
        onContrasenaCambia = viewModel::onContrasenaCambia,
        onAlternarContrasenaVisible = viewModel::onAlternarContrasenaVisible,
        onTerminosCambia = viewModel::onTerminosCambia,
        onRegistrar = viewModel::onRegistrar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistroContent(
    uiState: FormularioRegistroUiState,
    onRutCambia: (String) -> Unit,
    onNombreCambia: (String) -> Unit,
    onCorreoCambia: (String) -> Unit,
    onTelefonoCambia: (String) -> Unit,
    onContrasenaCambia: (String) -> Unit,
    onAlternarContrasenaVisible: () -> Unit,
    onTerminosCambia: (Boolean) -> Unit,
    onRegistrar: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Dimens.espacioPantalla,
                    end = Dimens.espacioPantalla,
                    top = innerPadding.calculateTopPadding() / 2,
                    bottom = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top

        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.padding(bottom = Dimens.espacioPequeno)
                    .size(44.dp).background(
                        color = MaterialTheme.colorScheme.background
                    )
            ) {
                Icon(
                    painter = painterResource((R.drawable.atras)),
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            TituloScreen(
                titulo = stringResource(R.string.titulo_registro),
                subtitulo = stringResource(R.string.subtitulo_registro),
            )
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Rut",
                color = MaterialTheme.colorScheme.onSurface,

            )
            Spacer(modifier = Modifier.height(2.dp))
            CampoTextoRut(
                valor = uiState.rut,
                onValorCambia = onRutCambia,
                etiqueta = stringResource(R.string.campo_rut),
                icono = R.drawable.rut,
                error = uiState.errorFormatoRut
            )
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            Text(
                text = "Nombre completo",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            CampoTexto(
                valor = uiState.nombre,
                onValorCambia = onNombreCambia,
                etiqueta = stringResource(R.string.campo_nombre),
                icono = R.drawable.ic_persona,
                error = uiState.errorNombre
            )
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            Text(
                text = "Telefóno",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            CampoTexto(
                valor = uiState.telefono,
                onValorCambia = onTelefonoCambia,
                etiqueta = stringResource(R.string.campo_telefono),
                icono = R.drawable.telefono,
                error = uiState.errorTelefono,
                tipoTeclado = KeyboardType.Phone
            )
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            Text(
                text = "Correo electrónico",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            CampoTexto(
                valor = uiState.correo,
                onValorCambia = onCorreoCambia,
                etiqueta = stringResource(R.string.campo_correo),
                icono = R.drawable.correo,
                error = uiState.errorFormatoCorreo,
                tipoTeclado = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            Text(
                text = "Contraseña",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            CampoContrasena(
                valor = uiState.contrasena,
                onValorCambia = onContrasenaCambia,
                etiqueta = stringResource(R.string.campo_contrasena),
                visible = uiState.contrasenaVisible,
                onAlternarContrasenaVisible = onAlternarContrasenaVisible,
                error = uiState.errorContrasena
            )

            Column {
                CasillaConTexto(
                    texto = stringResource(R.string.acepta_terminos),
                    marcado = uiState.aceptarTerminos,
                    onCambio = onTerminosCambia
                )
                uiState.errorTerminos?.let { mensaje ->
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = Dimens.espacioPequeno)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.espacioInterno)) {
                BotonPrincipal(
                    texto = stringResource(R.string.btn_registro),
                    onClick = onRegistrar,
                    icono = R.drawable.ic_check,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().clickable{}.padding(vertical = Dimens.espacioPequeno)
            )

            if (uiState.registroExitoso) {
                MensajeExitoso(uiState.nombre)
            }

            Spacer(Modifier.height(Dimens.espacioPantalla))
        }
    }
}
@Composable
private fun MensajeExitoso(nombre: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ExitoFondo, contentColor = Exito)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.espacioPequeno),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = null,
                tint = Exito,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
            Text(
                text = stringResource(R.string.registro_exitoso, nombre),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = Dimens.espacioPequeno)
            )
        }
    }
}