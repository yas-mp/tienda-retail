package com.example.tiendaretailsur.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.FormularioRegistroUiState
import com.example.tiendaretailsur.model.RolUsuario
import com.example.tiendaretailsur.ui.components.BotonPrincipal
import com.example.tiendaretailsur.ui.components.CampoContrasena
import com.example.tiendaretailsur.ui.components.CampoTexto
import com.example.tiendaretailsur.ui.components.CampoTextoRut
import com.example.tiendaretailsur.ui.components.CasillaConTexto
import com.example.tiendaretailsur.ui.components.TituloScreen
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.viewmodel.FormularioRegistroViewModel

@Composable
fun FormularioRegistroScreen(
    dbHelper: UsuarioDbHelper = UsuarioDbHelper(LocalContext.current),
    viewModel: FormularioRegistroViewModel = viewModel(
        factory = viewModelFactory {
            initializer { FormularioRegistroViewModel(dbHelper) }
        }
    ),
    onIrALoginClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FormularioRegistroContent(
        uiState = uiState,
        listaPreguntas = viewModel.listaPreguntasSeg,
        onRutCambia = viewModel::onRutCambia,
        onNombreCambia = viewModel::onNombreCambia,
        onCorreoCambia = viewModel::onCorreoCambia,
        onTelefonoCambia = viewModel::onTelefonoCambia,
        onContrasenaCambia = viewModel::onContrasenaCambia,
        onPreguntaSeleccionada = viewModel::onPreguntaSeleccionada,
        onRespuestaCambia = viewModel::onRespuestaCambia,
        onAlternarContrasenaVisible = viewModel::onAlternarContrasenaVisible,
        onSeleccionRol = viewModel::onSeleccionRol,
        onTerminosCambia = viewModel::onTerminosCambia,
        onLimpiarFormulario = viewModel::onLimpiarFormulario,
        onRegistrar = viewModel::onRegistrar,
        onIrALoginClick = onIrALoginClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistroContent(
    uiState: FormularioRegistroUiState,
    listaPreguntas: List<String> = emptyList(),
    onRutCambia: (String) -> Unit,
    onNombreCambia: (String) -> Unit,
    onCorreoCambia: (String) -> Unit,
    onTelefonoCambia: (String) -> Unit,
    onContrasenaCambia: (String) -> Unit,
    onPreguntaSeleccionada: (String) -> Unit = {},
    onRespuestaCambia: (String) -> Unit = {},
    onAlternarContrasenaVisible: () -> Unit,
    onSeleccionRol: (RolUsuario) -> Unit,
    onTerminosCambia: (Boolean) -> Unit,
    onLimpiarFormulario: () -> Unit,
    onRegistrar: () -> Unit,
    onIrALoginClick: () -> Unit = {}
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
                onClick = onIrALoginClick,
                modifier = Modifier
                    .padding(bottom = Dimens.espacioPequeno)
                    .size(44.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    painter = painterResource((R.drawable.atras)),
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            TituloScreen(
                titulo = stringResource(R.string.titulo_registro),
                subtitulo = stringResource(R.string.subtitulo_registro),
            )
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "RUT",
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
                text = "Teléfono",
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
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            var preguntaAbierta by remember { mutableStateOf(false) }
            Text(
                text = "Pregunta de seguridad",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))

            Box(modifier = Modifier.fillMaxWidth()){
                CampoTexto(
                    valor = if (uiState.preguntaSeleccionada.isEmpty()) "Selecciona una pregunta" else uiState.preguntaSeleccionada,
                    onValorCambia = {},
                    etiqueta = "Pregunta de seguridad",
                    icono = R.drawable.ic_persona,
                    error = uiState.errorPregunta
                )
                Box(
                    modifier = Modifier.matchParentSize()
                        .clickable {preguntaAbierta = true}
                )
                DropdownMenu(
                    expanded = preguntaAbierta,
                    onDismissRequest = { preguntaAbierta = false},
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    listaPreguntas.forEach { pregunta ->
                        DropdownMenuItem(
                            text = {Text(text = pregunta)},
                            onClick = {
                                onPreguntaSeleccionada(pregunta)
                                preguntaAbierta = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            Text(
                text = "Respuesta de seguridad",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            CampoTexto(
                valor = uiState.respuestaSeguridad,
                onValorCambia = onRespuestaCambia,
                etiqueta = "Tu respuesta",
                icono = R.drawable.ic_persona,
                error = uiState.errorRespuesta
            )
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

            var menuRolAbierto by remember { mutableStateOf(false) }
            Text(
                text = "Selecciona el rol",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))

            Box(modifier = Modifier.fillMaxWidth()){
                CampoTexto(
                    valor = uiState.rolSeleccionado.name,
                    onValorCambia = {},
                    etiqueta = "Seleccionar Rol",
                    icono = R.drawable.ic_persona
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { menuRolAbierto = true }
                )
                DropdownMenu(
                    expanded = menuRolAbierto,
                    onDismissRequest = { menuRolAbierto = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    RolUsuario.entries.forEach { rol ->
                        DropdownMenuItem(
                            text = { Text(text = rol.name) },
                            onClick = {
                                onSeleccionRol(rol)
                                menuRolAbierto = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.espacioInterno))

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
            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.espacioInterno)) {
                BotonPrincipal(
                    texto = stringResource(R.string.btn_registro),
                    onClick = onRegistrar,
                    icono = R.drawable.ic_check,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // ALERTA DE REGISTRO EXITOSO
            if (uiState.registroExitoso) {
                AlertDialog(
                    onDismissRequest = {
                        onLimpiarFormulario()
                        onIrALoginClick()
                    },
                    title = {
                        Text(
                            text = "¡Registro Exitoso!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text("El colaborador ${uiState.nombre} ha sido registrado correctamente en el sistema.")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                onLimpiarFormulario()
                                onIrALoginClick()
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }

            Spacer(Modifier.height(Dimens.espacioPantalla))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioRegistroContentPreview() {
    MiRegistroTheme {
        FormularioRegistroContent(
            uiState = FormularioRegistroUiState(
                rolSeleccionado = RolUsuario.OBSERVADOR
            ),
            onRutCambia = {},
            onNombreCambia = {},
            onCorreoCambia = {},
            onTelefonoCambia = {},
            onContrasenaCambia = {},
            onSeleccionRol = {},
            onAlternarContrasenaVisible = {},
            onTerminosCambia = {},
            onRegistrar = {},
            onLimpiarFormulario = {},
            onIrALoginClick = {}
        )
    }
}