package com.example.tiendaretailsur.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.AjustesAdminUiState
import com.example.tiendaretailsur.model.ConfiguracionAcceso
import com.example.tiendaretailsur.model.ConfiguracionPerfil
import com.example.tiendaretailsur.model.SeccionAjustesAdmin
import com.example.tiendaretailsur.model.Usuario
import com.example.tiendaretailsur.permisos.Permisos
import com.example.tiendaretailsur.ui.theme.Exito
import com.example.tiendaretailsur.ui.theme.ExitoFondo
import com.example.tiendaretailsur.viewmodel.AjustesAdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesAdminScreen(
    dbHelper: UsuarioDbHelper,
    seccionInicial: SeccionAjustesAdmin = SeccionAjustesAdmin.COLABORADORES,
    viewModel: AjustesAdminViewModel = viewModel(
        factory = viewModelFactory {
            initializer { AjustesAdminViewModel(dbHelper) }
        }
    ),
    onVolverClick: () -> Unit = {},
    onIrAMiPerfilClick: () -> Unit = {},
    onIrARegistroColaborador: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(seccionInicial) {
        viewModel.seleccionarSeccion(seccionInicial)
        viewModel.recargarDatos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Ajustes de Administrador",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Gestión de perfiles, colaboradores y tienda",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al panel"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = uiState.seccionActual.ordinal,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                SeccionAjustesAdmin.entries.forEach { seccion ->
                    val icono = when (seccion) {
                        SeccionAjustesAdmin.COLABORADORES -> Icons.Default.People
                        SeccionAjustesAdmin.PERFILES -> Icons.Default.Settings
                        SeccionAjustesAdmin.TIENDA -> Icons.Default.Store
                    }
                    Tab(
                        selected = uiState.seccionActual == seccion,
                        onClick = { viewModel.seleccionarSeccion(seccion) },
                        text = { Text(seccion.titulo) },
                        icon = { Icon(icono, contentDescription = null) }
                    )
                }
            }
            uiState.mensajeConfirmacion?.let { mensaje ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ExitoFondo,
                        contentColor = Exito
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Exito)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = mensaje,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Exito
                            )
                        }
                        TextButton(onClick = { viewModel.limpiarMensaje() }) {
                            Text("OK", color = Exito)
                        }
                    }
                }
            }
            when (uiState.seccionActual) {
                SeccionAjustesAdmin.COLABORADORES -> {
                    SeccionColaboradoresContent(
                        uiState = uiState,
                        onBusquedaCambia = viewModel::onBusquedaCambia,
                        onFiltroPerfilCambia = viewModel::onFiltroPerfilCambia,
                        onAlternarActivo = viewModel::alternarEstadoUsuario,
                        onEditarClick = viewModel::abrirEditorUsuario,
                        onIrARegistroColaborador = onIrARegistroColaborador
                    )
                }
                SeccionAjustesAdmin.PERFILES -> {
                    SeccionPerfilesContent(
                        perfiles = uiState.perfiles,
                        usuarios = uiState.usuarios,
                        onAlternarPerfil = viewModel::alternarEstadoPerfil,
                        onEditarPerfilClick = viewModel::abrirEditorPerfil
                    )
                }
                SeccionAjustesAdmin.TIENDA -> {
                    SeccionTiendaYCuentaContent(
                        metaConversionTexto = uiState.metaConversionTexto,
                        onMetaCambia = viewModel::onMetaConversionCambia,
                        onGuardarMeta = viewModel::guardarMetaConversion,
                        accesos = uiState.accesos,
                        onAlternarAcceso = viewModel::alternarEstadoAcceso,
                        onIrAMiPerfilClick = onIrAMiPerfilClick
                    )
                }
            }
        }
        if (uiState.usuarioEnEdicion != null) {
            DialogoEditarUsuario(
                uiState = uiState,
                onDismiss = viewModel::cerrarEditorUsuario,
                onNombreCambia = viewModel::onEditNombreCambia,
                onRutCambia = viewModel::onEditRutCambia,
                onCorreoCambia = viewModel::onEditCorreoCambia,
                onTelefonoCambia = viewModel::onEditTelefonoCambia,
                onCargoCambia = viewModel::onEditCargoCambia,
                onPerfilCambia = viewModel::onEditPerfilCambia,
                onActivoCambia = viewModel::onEditActivoCambia,
                onNuevaContrasenaCambia = viewModel::onEditNuevaContrasenaCambia,
                onGuardar = viewModel::guardarCambiosUsuario
            )
        }
        if (uiState.perfilEnEdicion != null) {
            DialogoEditarPerfil(
                uiState = uiState,
                onDismiss = viewModel::cerrarEditorPerfil,
                onNombreCambia = viewModel::onEditPerfilNombreCambia,
                onDescripcionCambia = viewModel::onEditPerfilDescripcionCambia,
                onHabilitadoCambia = viewModel::onEditPerfilHabilitadoCambia,
                onAplicarATodosCambia = viewModel::onAplicarATodosCambia,
                onGuardar = viewModel::guardarCambiosPerfil
            )
        }
    }
}

@Composable
private fun SeccionColaboradoresContent(
    uiState: AjustesAdminUiState,
    onBusquedaCambia: (String) -> Unit,
    onFiltroPerfilCambia: (String) -> Unit,
    onAlternarActivo: (Usuario, Boolean) -> Unit,
    onEditarClick: (Usuario) -> Unit,
    onIrARegistroColaborador: () -> Unit
) {
    val usuariosFiltrados = uiState.usuarios.filter { u ->
        val coincideTexto = uiState.busqueda.isBlank() ||
                u.nombreCompleto.contains(uiState.busqueda, ignoreCase = true) ||
                u.correo.contains(uiState.busqueda, ignoreCase = true) ||
                u.rut.contains(uiState.busqueda, ignoreCase = true)
        val coincidePerfil = uiState.filtroPerfil == "TODOS" || u.perfil == uiState.filtroPerfil
        coincideTexto && coincidePerfil
    }

    val totalActivos = uiState.usuarios.count { it.activo }
    val totalInactivos = uiState.usuarios.size - totalActivos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TarjetaContadorAdmin(
                titulo = "Total Cuentas",
                valor = "${uiState.usuarios.size}",
                modifier = Modifier.weight(1f),
            )
            TarjetaContadorAdmin(
                titulo = "Habilitados",
                valor = "$totalActivos",
                modifier = Modifier.weight(1f)
            )
            TarjetaContadorAdmin(
                titulo = "Inactivos",
                valor = "$totalInactivos",
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = uiState.busqueda,
            onValueChange = onBusquedaCambia,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por nombre, correo o RUT...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val opciones = listOf("TODOS", "SUBGERENTE", "OBSERVADOR")
            opciones.forEach { codigo ->
                FilterChip(
                    selected = uiState.filtroPerfil == codigo,
                    onClick = { onFiltroPerfilCambia(codigo) },
                    label = { Text(if (codigo == "TODOS") "Todos los perfiles" else codigo) }
                )
            }
        }
        Button(
            onClick = onIrARegistroColaborador,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar Nuevo Colaborador", fontWeight = FontWeight.Bold)
        }
        usuariosFiltrados.forEach { usuario ->
            val perfilHabilitado = uiState.perfiles.find { it.nombre == usuario.perfil || it.codigo == usuario.perfil }?.habilitado ?: true
            TarjetaColaboradorItem(
                usuario = usuario,
                perfilGlobalHabilitado = perfilHabilitado,
                onAlternarActivo = { nuevoEstado -> onAlternarActivo(usuario, nuevoEstado) },
                onEditarClick = { onEditarClick(usuario) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TarjetaContadorAdmin(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TarjetaColaboradorItem(
    usuario: Usuario,
    perfilGlobalHabilitado: Boolean,
    onAlternarActivo: (Boolean) -> Unit,
    onEditarClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(
                                    color = if (usuario.activo && perfilGlobalHabilitado) Exito else MaterialTheme.colorScheme.error,
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = usuario.nombreCompleto,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "RUT: ${usuario.rut}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = usuario.perfil,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

            Text(
                text = "Correo: ${usuario.correo}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Teléfono: +56 ${usuario.telefono}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (!perfilGlobalHabilitado) {
                Text(
                    text = "Aviso: El perfil ${usuario.perfil} está deshabilitado globalmente",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = usuario.activo,
                        onCheckedChange = onAlternarActivo
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (usuario.activo) "Habilitado" else "Deshabilitado",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                OutlinedButton(onClick = onEditarClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Modificar datos")
                }
            }
        }
    }
}

@Composable
private fun SeccionPerfilesContent(
    perfiles: List<ConfiguracionPerfil>,
    usuarios: List<Usuario>,
    onAlternarPerfil: (ConfiguracionPerfil, Boolean) -> Unit,
    onEditarPerfilClick: (ConfiguracionPerfil) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Perfiles de Usuario de la Aplicación",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Habilita o deshabilita perfiles completos y edita su configuración.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )

        perfiles.forEach { perfil ->
            val cantidadUsuarios = usuarios.count { it.perfil == perfil.nombre || it.perfil == perfil.codigo }
            val cantidadActivos = usuarios.count { (it.perfil == perfil.nombre || it.perfil == perfil.codigo) && it.activo }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${perfil.codigo} - ${perfil.nombre}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "$cantidadActivos de $cantidadUsuarios colaboradores activos en este perfil",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            )
                        }
                        Switch(
                            checked = perfil.habilitado,
                            onCheckedChange = { onAlternarPerfil(perfil, it) }
                        )
                    }

                    Text(
                        text = perfil.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                    Text(
                        text = "Permisos asignados:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    perfil.permisosActivos.forEach { permiso ->
                        Text(
                            text = "• $permiso",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { onEditarPerfilClick(perfil) }) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Configurar perfil")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeccionTiendaYCuentaContent(
    metaConversionTexto: String,
    onMetaCambia: (String) -> Unit,
    onGuardarMeta: () -> Unit,
    accesos: List<ConfiguracionAcceso>,
    onAlternarAcceso: (String, Boolean) -> Unit,
    onIrAMiPerfilClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Cuenta de Administrador y Dispositivo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Actualiza tu fotografía de perfil o gestiona los permisos de cámara y almacenamiento del teléfono.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onIrAMiPerfilClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Mi Perfil")
                    }
                    OutlinedButton(
                        onClick = { Permisos.abrirAjustes(context) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Permisos App")
                    }
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Meta Mensual de Conversión",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Define el porcentaje objetivo de boletas sobre ingresos totales para comparar los reportes semanales.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = metaConversionTexto,
                        onValueChange = onMetaCambia,
                        label = { Text("Meta (%)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = onGuardarMeta) {
                        Text("Guardar Meta")
                    }
                }
            }
        }

        // Estado operativo de accesos
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Estado de Accesos de la Tienda",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Habilita o marca como cerrado un acceso por mantención o evento.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                accesos.forEach { acceso ->
                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${acceso.codigo} - ${acceso.nombre}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = acceso.ubicacion,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            )
                        }
                        Switch(
                            checked = acceso.habilitado,
                            onCheckedChange = { onAlternarAcceso(acceso.codigo, it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogoEditarUsuario(
    uiState: AjustesAdminUiState,
    onDismiss: () -> Unit,
    onNombreCambia: (String) -> Unit,
    onRutCambia: (String) -> Unit,
    onCorreoCambia: (String) -> Unit,
    onTelefonoCambia: (String) -> Unit,
    onCargoCambia: (String) -> Unit,
    onPerfilCambia: (String) -> Unit,
    onActivoCambia: (Boolean) -> Unit,
    onNuevaContrasenaCambia: (String) -> Unit,
    onGuardar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modificar Colaborador") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.errorEdicionUsuario?.let { err ->
                    Text(text = err, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }

                OutlinedTextField(
                    value = uiState.editNombre,
                    onValueChange = onNombreCambia,
                    label = { Text("Nombre Completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.editRut,
                    onValueChange = onRutCambia,
                    label = { Text("RUT") },
                    singleLine = true,
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.editCorreo,
                    onValueChange = onCorreoCambia,
                    label = { Text("Correo Electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.editTelefono,
                    onValueChange = onTelefonoCambia,
                    label = { Text("Teléfono") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.editNuevaContrasena,
                    onValueChange = onNuevaContrasenaCambia,
                    label = { Text("Nueva Contraseña (Opcional)") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Switch(checked = uiState.editActivo, onCheckedChange = onActivoCambia)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (uiState.editActivo) "Usuario Activo" else "Usuario Inactivo")
                }
            }
        },
        confirmButton = {
            Button(onClick = onGuardar) {
                Text("Guardar Cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun DialogoEditarPerfil(
    uiState: AjustesAdminUiState,
    onDismiss: () -> Unit,
    onNombreCambia: (String) -> Unit,
    onDescripcionCambia: (String) -> Unit,
    onHabilitadoCambia: (Boolean) -> Unit,
    onAplicarATodosCambia: (Boolean) -> Unit,
    onGuardar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Configurar Perfil ${uiState.perfilEnEdicion?.codigo}") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = uiState.editPerfilNombre,
                    onValueChange = onNombreCambia,
                    label = { Text("Nombre del Perfil") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.editPerfilDescripcion,
                    onValueChange = onDescripcionCambia,
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Switch(checked = uiState.editPerfilHabilitado, onCheckedChange = onHabilitadoCambia)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (uiState.editPerfilHabilitado) "Perfil Habilitado" else "Perfil Deshabilitado")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(checked = uiState.aplicarEstadoATodosLosUsuariosDelPerfil, onCheckedChange = onAplicarATodosCambia)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Aplicar estado a todos los usuarios de este perfil", style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            Button(onClick = onGuardar) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
