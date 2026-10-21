package com.example.tiendaretailsur.ui.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.data.ConvertidorImagen
import com.example.tiendaretailsur.permisos.Permisos
import com.example.tiendaretailsur.ui.components.AvatarCircular
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.viewmodel.PerfilViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    isAdmin: Boolean = true,
    onVolver: () -> Unit = {},
    viewModel: PerfilViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val perfil = uiState.perfil
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val foto = remember(uiState.avatarBytes) {
        uiState.avatarBytes?.let { bytes -> ConvertidorImagen.aBitmap(bytes)?.asImageBitmap() }
    }

    val lanzadorCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            val bytes = ConvertidorImagen.aBytes(bitmap)
            viewModel.setAvatarBytes(bytes)
        }
    }

    val pedirPermisoCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            try {
                lanzadorCamara.launch(null)
            } catch (e: ActivityNotFoundException) { }
        } else {
            scope.launch { snackbarHostState.showSnackbar("Permiso de cámara denegado") }
        }
    }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        val uri = resultado.data?.data
        if (resultado.resultCode == Activity.RESULT_OK && uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    val bytes = ConvertidorImagen.aBytes(bitmap)
                    viewModel.setAvatarBytes(bytes)
                }
            } catch (e: Exception) { }
        }
    }

    val pedirPermisoGaleria = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            lanzadorGaleria.launch(intent)
        } else {
            scope.launch { snackbarHostState.showSnackbar("Permiso de galería denegado") }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isAdmin) "Perfil de Administrador" else "Perfil de Colaborador") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(painter = painterResource(R.drawable.ic_flecha), contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Avatar Circular
            AvatarCircular(
                imagen = R.drawable.ic_persona,
                descripcion = "Avatar de usuario",
                foto = foto
            )

            Text(
                text = perfil.nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = perfil.cargo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Tarjeta con información del colaborador (Nombre, Correo, Turno)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Información del Colaborador",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Correo electrónico:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Text(text = perfil.correo, style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Turno asignado:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Badge(containerColor = MaterialTheme.colorScheme.primary) {
                            Text(text = perfil.turno, color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }

            // Tarjeta para cambiar foto de perfil (Cámara o Galería)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Foto de Perfil", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = if (isAdmin) "Como administrador, puedes actualizar tu foto y gestionar el perfil." else "Como colaborador, esta es la única sección que puedes modificar en tu perfil.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                if (Permisos.tiene(context, Permisos.CAMARA)) {
                                    lanzadorCamara.launch(null)
                                } else {
                                    pedirPermisoCamara.launch(Permisos.CAMARA)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Tomar Foto")
                        }

                        OutlinedButton(
                            onClick = {
                                if (Permisos.tiene(context, Permisos.IMAGENES)) {
                                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                    lanzadorGaleria.launch(intent)
                                } else {
                                    pedirPermisoGaleria.launch(Permisos.IMAGENES)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Galería")
                        }
                    }
                }
            }

            if (isAdmin) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Información del Sistema (Admin)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "Rol: Subgerente / Administrador Comercial", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Acceso total a reportes y configuración de accesos.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Perfil Colaborador - Oscuro")
@Composable
private fun PerfilColaboradorOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Perfil Colaborador - Claro")
@Composable
private fun PerfilColaboradorClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Perfil Admin - Oscuro")
@Composable
private fun PerfilAdminOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = true)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Perfil Admin - Claro")
@Composable
private fun PerfilAdminClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = true)
        }
    }
}