package com.example.tiendaretailsur.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.LoginUiState
import com.example.tiendaretailsur.model.Usuario
import com.example.tiendaretailsur.ui.theme.Dimens
import com.example.tiendaretailsur.ui.theme.Exito
import com.example.tiendaretailsur.ui.theme.ExitoFondo
import com.example.tiendaretailsur.ui.theme.GrisDeshabilitada
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    dbHelper: UsuarioDbHelper,
    viewModel: LoginViewModel = viewModel(
        factory = viewModelFactory {
            initializer { LoginViewModel(dbHelper) }
        }
    ),
    onLoginSuccess: (Usuario) -> Unit = {},
    onOlvideContrasenaClick: () -> Unit = {},
    onCrearCuentaClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    //  Navegación automática tan pronto el login sea exitoso
    LaunchedEffect(uiState.loginExitoso, uiState.usuarioAutenticado) {
        if (uiState.loginExitoso && uiState.usuarioAutenticado != null) {
            onLoginSuccess(uiState.usuarioAutenticado!!)
        }
    }

    LoginContent(
        uiState = uiState,
        onCorreoChanged = viewModel::onCorreoChanged,
        onContrasenaChanged = viewModel::onContrasenaChanged,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
        onLoginClick = {
            viewModel.login()
        },
        onOlvideContrasenaClick = onOlvideContrasenaClick,
        onCrearCuentaClick = onCrearCuentaClick
    )
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onCorreoChanged: (String) -> Unit = {},
    onContrasenaChanged: (String) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onOlvideContrasenaClick: () -> Unit = {},
    onCrearCuentaClick: () -> Unit = {}
) {
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
            horizontalAlignment = Alignment.Start
        ) {
            // Encabezado: Icono en recuadro + "Aforo"
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = colores.surface,
                            shape = formas.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_puerta),
                        contentDescription = stringResource(id = R.string.login_titulo_principal),
                        modifier = Modifier.size(Dimens.tamanoIcono),
                        tint = colores.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(Dimens.espacioMedio))
                Text(
                    text = stringResource(id = R.string.login_titulo_principal),
                    style = tipografia.titleLarge,
                    color = colores.onBackground
                )
            }

            Spacer(modifier = Modifier.height(Dimens.espacioGrande))

            // Tarjeta Superior ("CADA ACCESO CUENTA")
            Card(
                shape = formas.large,
                colors = CardDefaults.cardColors(containerColor = colores.surface),
                border = BorderStroke(1.dp, colores.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.elevacionTarjeta),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.espacioMedio),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = colores.primary,
                                shape = formas.medium
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_escaner),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = colores.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(Dimens.espacioMedio))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(color = Exito, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(Dimens.espacioChico))
                            Text(
                                text = stringResource(id = R.string.login_tarjeta_etiqueta),
                                style = tipografia.labelLarge.copy(fontSize = 11.sp),
                                color = colores.onSurfaceVariant.copy(alpha = 0.75f)
                            )
                        }
                        Spacer(modifier = Modifier.height(Dimens.espacioMini))
                        Text(
                            text = stringResource(id = R.string.login_tarjeta_texto),
                            style = tipografia.titleMedium.copy(fontWeight = FontWeight.Normal),
                            color = colores.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.espacioGrande))

            // Saludo y Descripción
            Text(
                text = stringResource(id = R.string.login_saludo),
                style = tipografia.titleLarge.copy(fontSize = 30.sp, lineHeight = 36.sp),
                color = colores.onBackground
            )
            Spacer(modifier = Modifier.height(Dimens.espacioChico))
            Text(
                text = stringResource(id = R.string.login_descripcion),
                style = tipografia.bodyMedium,
                color = colores.onBackground.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(Dimens.espacioGrande))

            // Campo Correo Electrónico o RUT
            Text(
                text = stringResource(id = R.string.login_label_correo),
                style = tipografia.labelLarge,
                color = colores.onBackground
            )
            Spacer(modifier = Modifier.height(Dimens.espacioChico))
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = onCorreoChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.login_hint_correo),
                        style = tipografia.bodyMedium,
                        color = colores.onSurface.copy(alpha = 0.7f)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_correo),
                        contentDescription = stringResource(id = R.string.login_label_correo),
                        modifier = Modifier.size(Dimens.tamanoIcono),
                        tint = colores.onSurface
                    )
                },
                shape = formas.medium,
                isError = uiState.errorCorreo != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colores.surface,
                    unfocusedContainerColor = colores.surface,
                    errorContainerColor = colores.surface,
                    focusedTextColor = colores.onSurface,
                    unfocusedTextColor = colores.onSurface,
                    focusedBorderColor = colores.onBackground,
                    unfocusedBorderColor = colores.onSurface.copy(alpha = 0.5f),
                    cursorColor = colores.onSurface
                )
            )
            if (uiState.errorCorreo != null) {
                Spacer(modifier = Modifier.height(Dimens.espacioMini))
                Text(
                    text = uiState.errorCorreo!!,
                    color = colores.error,
                    style = tipografia.bodyMedium.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(start = Dimens.espacioChico)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.espacioMedio))

            Text(
                text = stringResource(id = R.string.login_label_contrasena),
                style = tipografia.labelLarge,
                color = colores.onBackground
            )
            Spacer(modifier = Modifier.height(Dimens.espacioChico))
            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = onContrasenaChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.login_hint_contrasena),
                        style = tipografia.bodyMedium,
                        color = colores.onSurface.copy(alpha = 0.7f)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_candado),
                        contentDescription = stringResource(id = R.string.login_label_contrasena),
                        modifier = Modifier.size(Dimens.tamanoIcono),
                        tint = colores.onSurface
                    )
                },
                trailingIcon = {
                    IconButton(onClick = onTogglePasswordVisibility) {
                        val iconoOjo = if (uiState.isPasswordVisible) {
                            R.drawable.ic_ojo
                        } else {
                            R.drawable.ic_ojo_tachado
                        }
                        Icon(
                            painter = painterResource(id = iconoOjo),
                            contentDescription = stringResource(id = R.string.login_label_contrasena),
                            modifier = Modifier.size(Dimens.tamanoIcono),
                            tint = colores.onSurface
                        )
                    }
                },
                visualTransformation = if (uiState.isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                shape = formas.medium,
                isError = uiState.errorContrasena != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colores.surface,
                    unfocusedContainerColor = colores.surface,
                    errorContainerColor = colores.surface,
                    focusedTextColor = colores.onSurface,
                    unfocusedTextColor = colores.onSurface,
                    focusedBorderColor = colores.onBackground,
                    unfocusedBorderColor = colores.onSurface.copy(alpha = 0.5f),
                    cursorColor = colores.onSurface
                )
            )
            if (uiState.errorContrasena != null) {
                Spacer(modifier = Modifier.height(Dimens.espacioMini))
                Text(
                    text = uiState.errorContrasena!!,
                    color = colores.error,
                    style = tipografia.bodyMedium.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(start = Dimens.espacioChico)
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = onOlvideContrasenaClick) {
                    Text(
                        text = stringResource(id = R.string.login_olvide_contrasena),
                        style = tipografia.labelLarge.copy(fontSize = 13.sp),
                        color = colores.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.espacioChico))

            Button(
                onClick = onLoginClick,
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.alturaBoton),
                shape = RoundedCornerShape(Dimens.radioBoton),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = Dimens.elevacionBoton),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colores.primary,
                    contentColor = colores.onPrimary,
                    disabledContainerColor = GrisDeshabilitada
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(Dimens.tamanoIcono),
                        color = colores.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_btn_ingresar),
                            style = tipografia.labelLarge,
                            color = colores.onPrimary
                        )
                        Spacer(modifier = Modifier.width(Dimens.espacioChico))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_flecha),
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.tamanoIcono),
                            tint = colores.onPrimary
                        )
                    }
                }
            }

            if (uiState.loginExitoso && uiState.usuarioAutenticado != null) {
                Spacer(modifier = Modifier.height(Dimens.espacioMedio))
                MensajeLoginExitoso(usuario = uiState.usuarioAutenticado!!)
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(Dimens.espacioGrande))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_escudo),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = colores.onBackground.copy(alpha = 0.75f)
                    )
                    Spacer(modifier = Modifier.width(Dimens.espacioMini))
                    Text(
                        text = stringResource(id = R.string.login_footer),
                        style = tipografia.bodyMedium.copy(fontSize = 12.sp),
                        color = colores.onBackground.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MensajeLoginExitoso(usuario: Usuario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = ExitoFondo,
            contentColor = Exito
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.espacioMedio),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = Exito,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
            Spacer(modifier = Modifier.width(Dimens.espacioMedio))
            Column {
                Text(
                    text = "Acceso autorizado: ${usuario.nombreCompleto}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Exito
                )
                Text(
                    text = "• Perfil ${usuario.perfil} (RUT: ${usuario.rut})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Modo Claro")
@Composable
fun LoginScreenClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        LoginContent(
            uiState = LoginUiState(
                correo = "admin@rincondelsur.cl",
                contrasena = "1234"
            )
        )
    }
}

@Preview(
    showBackground = true,
    name = "Modo Oscuro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        LoginContent(
            uiState = LoginUiState(
                correo = "admin@rincondelsur.cl",
                contrasena = "1234"
            )
        )
    }
}