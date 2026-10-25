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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaretailsur.ui.components.UserHeader
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.viewmodel.MenuPrincipalViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(
    isAdmin: Boolean = true,
    modifier: Modifier = Modifier,
    viewModel: MenuPrincipalViewModel = viewModel(),
    onIrAdminDashboard: () -> Unit = {},
    onIrPerfil: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    LaunchedEffect(isAdmin) {
        viewModel.cargarDatosDesdeDb(isAdmin)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val ultimoResumen = uiState.ultimoResumen
    var accesoSeleccionado by remember { mutableStateOf<String?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Tienda Retail Sur",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isAdmin) "Panel Administrador" else "Panel Colaborador",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                NavigationDrawerItem(
                    label = { Text("Menú Principal") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Mi Perfil (Editar Foto)") },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onIrPerfil()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                if (isAdmin) {
                    NavigationDrawerItem(
                        label = { Text("Panel Avanzado (Dashboard)") },
                        icon = { Icon(Icons.Default.Store, contentDescription = null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onIrAdminDashboard()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onCerrarSesion()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(if (isAdmin) "Menú Principal (Admin)" else "Menú Principal (Colaborador)") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú lateral")
                        }
                    }
                )
            }
        ) { innerPadding ->

            if (isAdmin) {
                //  VISTA DE ADMINISTRADOR
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        UserHeader(
                            userName = "Administrador",
                            title = "Menú Principal",
                            avatarBytes = uiState.avatarBytes
                        )
                    }

                    // baner: Total de ingresos de las 4 puertas y porcentajes
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Resumen Global (4 Puertas de Acceso)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = "Total Ingresos", style = MaterialTheme.typography.bodySmall)
                                        Text(
                                            text = "${uiState.totalIngresosCuatroPuertas} pers.",
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(text = "Distribución de Aforo", style = MaterialTheme.typography.bodySmall)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Tránsito: ${uiState.porcentajeTransito}%",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "Clientes: ${uiState.porcentajeClientes}%",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (ultimoResumen != null) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "Último Cierre Diario (${ultimoResumen.fecha} - ${ultimoResumen.diaSemana})",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        MetricItem("Ingresos", "${ultimoResumen.totalIngresos}")
                                        MetricItem("Boletas", "${ultimoResumen.transacciones}")
                                        MetricItem("Conversión", "${ultimoResumen.conversion}%")
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Distribución por Accesos (4 Puertas)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    items(uiState.listaAccesos) { acceso ->
                        val cantidad = uiState.conteosPorPuerta[acceso.id] ?: 0

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onSurface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Puerta ${acceso.id} · ${acceso.nombre}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = acceso.descripcion,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "$cantidad pers.",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // boton para ver el Panel Avanzado
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = onIrAdminDashboard,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Ver Panel Avanzado (Admin Dashboard)",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                //  VISTA DE USUARIO
                if (accesoSeleccionado == null) {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            UserHeader(
                                userName = "Colaborador",
                                title = "Selección de Acceso",
                                avatarBytes = uiState.avatarBytes
                            )
                        }

                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Puertas de Acceso Disponibles",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Selecciona la puerta de acceso donde te encuentras ubicado para iniciar el registro.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }

                        items(uiState.listaAccesos) { acceso ->
                            val esSeleccionado = accesoSeleccionado == acceso.id

                            Card(
                                onClick = { accesoSeleccionado = acceso.id },
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (esSeleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                    contentColor = if (esSeleccionado) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Puerta ${acceso.id} - ${acceso.nombre}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = acceso.descripcion,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                        )
                                    }
                                    if (esSeleccionado) {
                                        Badge(containerColor = MaterialTheme.colorScheme.primary) {
                                            Text("Seleccionado", color = MaterialTheme.colorScheme.onPrimary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // selecciona puerta -> abre formulario
                    Box(modifier = Modifier.padding(innerPadding)) {
                        FormularioConteoScreen(
                            puertaInicial = accesoSeleccionado!!,
                            modifier = modifier,
                            onVolver = { accesoSeleccionado = null }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Admin - Oscuro")
@Composable
private fun MenuPrincipalAdminOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = true)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Admin - Claro")
@Composable
private fun MenuPrincipalAdminClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = true)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Colaborador - Oscuro")
@Composable
private fun MenuPrincipalColaboradorOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Colaborador - Claro")
@Composable
private fun MenuPrincipalColaboradorClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}