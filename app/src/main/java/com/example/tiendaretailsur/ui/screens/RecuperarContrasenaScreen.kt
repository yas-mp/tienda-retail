package com.example.tiendaretailsur.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.model.PasoRecuperacion
import com.example.tiendaretailsur.ui.components.CampoContrasena
import com.example.tiendaretailsur.ui.components.CampoTexto
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.Exito
import com.example.tiendaretailsur.ui.theme.ExitoFondo
import com.example.tiendaretailsur.viewmodel.RecuperarContrasenaViewModel

@Composable
fun RecuperarContrasenaScreen(
    viewModel: RecuperarContrasenaViewModel = viewModel(),
    onVolverALoginClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val colores = MaterialTheme.colorScheme
    val tipografia = MaterialTheme.typography
    val formas = MaterialTheme.shapes

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colores.background)
            .padding(Dimens.espacioGrande)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioMedio)
        ) {
            // Encabezado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(color = colores.surface, shape = formas.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_candado),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.tamanoIcono),
                        tint = colores.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(Dimens.espacioMedio))
                Text(
                    text = "Recuperar acceso",
                    style = tipografia.titleLarge,
                    color = colores.onBackground
                )
            }

            Text(
                text = "Valida tu identidad respondiendo tus preguntas de seguridad para restablecer tu contraseña.",
                style = tipografia.bodyMedium,
                color = colores.onBackground.copy(alpha = 0.85f)
            )

            // Busca Usuario por Correo o RUT
            if (uiState.pasoActual == PasoRecuperacion.BUSCAR_USUARIO) {
                Card(
                    shape = formas.large,
                    colors = CardDefaults.cardColors(containerColor = colores.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.espacioMedio),
                        verticalArrangement = Arrangement.spacedBy(Dimens.espacioMedio)
                    ) {
                        Text(
                            text = "Paso 1: Identifica tu cuenta",
                            style = tipografia.titleMedium,
                            color = colores.onSurfaceVariant
                        )

                        CampoTexto(
                            valor = uiState.identificadorUsuario,
                            onValorCambia = viewModel::onIdentificadorCambia,
                            etiqueta = "Correo electrónico o RUT",
                            icono = R.drawable.ic_persona,
                            error = uiState.errorUsuario
                        )

                        Button(
                            onClick = { viewModel.buscarUsuario() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.alturaBoton),
                            shape = RoundedCornerShape(Dimens.radioBoton),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colores.primary,
                                contentColor = colores.onPrimary
                            )
                        ) {
                            Text(
                                text = "Buscar usuario",
                                style = tipografia.labelLarge
                            )
                        }
                    }
                }
            }

            // Preguntas de Seguridad
            if (uiState.pasoActual == PasoRecuperacion.PREGUNTAS_SEGURIDAD) {
                Card(
                    shape = formas.large,
                    colors = CardDefaults.cardColors(containerColor = colores.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.espacioMedio),
                        verticalArrangement = Arrangement.spacedBy(Dimens.espacioMedio)
                    ) {
                        Text(
                            text = "Paso 2: Preguntas de seguridad",
                            style = tipografia.titleMedium,
                            color = colores.onSurfaceVariant
                        )
                        Text(
                            text = "Colaborador: ${uiState.nombreUsuarioEncontrado}",
                            style = tipografia.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = colores.onSurfaceVariant
                        )

                        Text(
                            text = "1. ${uiState.pregunta1}",
                            style = tipografia.labelLarge,
                            color = colores.onSurfaceVariant
                        )
                        CampoTexto(
                            valor = uiState.respuesta1,
                            onValorCambia = viewModel::onRespuesta1Cambia,
                            etiqueta = "Tu respuesta 1",
                            error = uiState.errorRespuesta1
                        )

                        Text(
                            text = "2. ${uiState.pregunta2}",
                            style = tipografia.labelLarge,
                            color = colores.onSurfaceVariant
                        )
                        CampoTexto(
                            valor = uiState.respuesta2,
                            onValorCambia = viewModel::onRespuesta2Cambia,
                            etiqueta = "Tu respuesta 2",
                            error = uiState.errorRespuesta2
                        )

                        Button(
                            onClick = { viewModel.verificarRespuestas() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.alturaBoton),
                            shape = RoundedCornerShape(Dimens.radioBoton),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colores.primary,
                                contentColor = colores.onPrimary
                            )
                        ) {
                            Text(
                                text = "Verificar respuestas",
                                style = tipografia.labelLarge
                            )
                        }
                    }
                }
            }

            // Ingresar Nueva Contraseña
            if (uiState.pasoActual == PasoRecuperacion.CAMBIAR_CONTRASENA) {
                Card(
                    shape = formas.large,
                    colors = CardDefaults.cardColors(containerColor = colores.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.espacioMedio),
                        verticalArrangement = Arrangement.spacedBy(Dimens.espacioMedio)
                    ) {
                        Text(
                            text = "Paso 3: Crea tu nueva contraseña",
                            style = tipografia.titleMedium,
                            color = colores.onSurfaceVariant
                        )

                        CampoContrasena(
                            valor = uiState.nuevaContrasena,
                            onValorCambia = viewModel::onNuevaContrasenaCambia,
                            etiqueta = "Nueva contraseña",
                            visible = uiState.nuevaContrasenaVisible,
                            onAlternarContrasenaVisible = viewModel::onAlternarNuevaVisible,
                            error = uiState.errorNuevaContrasena
                        )

                        CampoContrasena(
                            valor = uiState.confirmarContrasena,
                            onValorCambia = viewModel::onConfirmarContrasenaCambia,
                            etiqueta = "Confirmar nueva contraseña",
                            visible = uiState.confirmarContrasenaVisible,
                            onAlternarContrasenaVisible = viewModel::onAlternarConfirmarVisible,
                            error = uiState.errorConfirmarContrasena
                        )

                        Button(
                            onClick = { viewModel.guardarNuevaContrasena() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.alturaBoton),
                            shape = RoundedCornerShape(Dimens.radioBoton),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colores.primary,
                                contentColor = colores.onPrimary
                            )
                        ) {
                            Text(
                                text = "Actualizar contraseña",
                                style = tipografia.labelLarge
                            )
                        }
                    }
                }
            }

            // Confirmación de éxito y muestra del Hash SHA-256 generado
            if (uiState.pasoActual == PasoRecuperacion.COMPLETADO) {
                Card(
                    shape = formas.large,
                    colors = CardDefaults.cardColors(
                        containerColor = ExitoFondo,
                        contentColor = Exito
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.espacioMedio),
                        verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
                    ) {
                        Text(
                            text = "¡Contraseña actualizada con éxito!",
                            style = tipografia.titleMedium,
                            color = Exito
                        )
                        Text(
                            text = "Tu nueva contraseña ha sido encriptada con SHA-256 (password_hash):",
                            style = tipografia.bodyMedium,
                            color = colores.onPrimary
                        )
                        Text(
                            text = uiState.nuevoHashGenerado,
                            style = tipografia.bodyMedium.copy(fontSize = 11.sp),
                            color = Exito
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón para volver al Login en cualquier momento
            TextButton(
                onClick = onVolverALoginClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Volver al inicio de sesión",
                    style = tipografia.labelLarge,
                    color = colores.onBackground
                )
            }
        }
    }
}