package com.example.tiendaretailsur.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaretailsur.model.FormularioConteoUiState
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.viewmodel.FormularioConteoViewModel

@Composable
fun FormularioConteoScreen(
    puertaInicial: String = "A1",
    modifier: Modifier = Modifier,
    viewModel: FormularioConteoViewModel = viewModel(),
    onVolver: () -> Unit = {}
) {
    // Al entrar o cambiar la puerta inicial, validamos y configuramos el estado
    LaunchedEffect(puertaInicial) {
        viewModel.setPuerta(puertaInicial)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val estaPuertaBloqueada = viewModel.estaPuertaActualBloqueada()

    FormularioConteoContent(
        uiState = uiState,
        estaBloqueada = estaPuertaBloqueada,
        modifier = modifier,
        onIncrementarTransito = viewModel::incrementarTransito,
        onDecrementarTransito = viewModel::decrementarTransito,
        onIncrementarClientes = viewModel::incrementarClientes,
        onDecrementarClientes = viewModel::decrementarClientes,
        onSeleccionarEdad = viewModel::seleccionarRangoEtario,
        onSeleccionarCompania = viewModel::seleccionarCompania,
        onEnviar = viewModel::enviarFormulario,
        onDismissExitoso = viewModel::limpiarExitoso,
        onVolver = onVolver
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormularioConteoContent(
    uiState: FormularioConteoUiState,
    estaBloqueada: Boolean = false,
    modifier: Modifier = Modifier,
    onIncrementarTransito: () -> Unit,
    onDecrementarTransito: () -> Unit,
    onIncrementarClientes: () -> Unit,
    onDecrementarClientes: () -> Unit,
    onSeleccionarEdad: (String) -> Unit,
    onSeleccionarCompania: (String) -> Unit,
    onEnviar: () -> Unit,
    onDismissExitoso: () -> Unit = {},
    onVolver: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onVolver) {
                    Text("Volver Accesos")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Registro de Aforo en Vivo",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Puerta Activa: ${uiState.puertaAcceso}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (estaBloqueada) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))

            if (estaBloqueada) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Este acceso se encuentra CERRADO por administración (mantención/evento). No se pueden enviar conteos.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tránsito (Solo Atajo)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Personas que cruzaron por la tienda sin comprar.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = "${uiState.cantidadTransito}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDecrementarTransito,
                            enabled = !estaBloqueada,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Restar (-1)")
                        }
                        Button(
                            onClick = onIncrementarTransito,
                            enabled = !estaBloqueada,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Sumar (+1)")
                        }
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Cliente (Compra)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Personas que ingresaron con intención de compra.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = "${uiState.cantidadClientes}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDecrementarClientes,
                            enabled = !estaBloqueada,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Restar (-1)")
                        }
                        Button(
                            onClick = onIncrementarClientes,
                            enabled = !estaBloqueada,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Sumar (+1)")
                        }
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Rango Etario Estimado", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("18–25", "26–40", "41–60", "61+").forEach { rango ->
                            FilterChip(
                                enabled = !estaBloqueada,
                                selected = uiState.rangoEtario == rango,
                                onClick = { onSeleccionarEdad(rango) },
                                label = { Text(rango) }
                            )
                        }
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Tipo de Compañía", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Solo", "Pareja").forEach { comp ->
                                FilterChip(
                                    enabled = !estaBloqueada,
                                    modifier = Modifier.weight(1f),
                                    selected = uiState.compania == comp,
                                    onClick = { onSeleccionarCompania(comp) },
                                    label = {
                                        Text(
                                            text = comp,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Con niños", "Grupo").forEach { comp ->
                                FilterChip(
                                    enabled = !estaBloqueada,
                                    modifier = Modifier.weight(1f),
                                    selected = uiState.compania == comp,
                                    onClick = { onSeleccionarCompania(comp) },
                                    label = {
                                        Text(
                                            text = comp,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = onEnviar,
                enabled = !estaBloqueada,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Enviar Registro de Aforo", style = MaterialTheme.typography.titleMedium)
            }
            if (estaBloqueada) {
                AlertDialog(
                    onDismissRequest = onVolver,
                    title = { Text("Acceso Inhabilitado") },
                    text = { Text("La puerta ${uiState.puertaAcceso} ha sido cerrada temporalmente por administración. Por favor selecciona otra puerta.") },
                    confirmButton = {
                        Button(onClick = onVolver) {
                            Text("Volver a la Lista")
                        }
                    }
                )
            }

            if (uiState.envioExitoso) {
                AlertDialog(
                    onDismissRequest = onDismissExitoso,
                    title = { Text("¡Registro Exitoso!") },
                    text = { Text("Se ha registrado correctamente el aforo para la puerta ${uiState.puertaAcceso}.") },
                    confirmButton = {
                        Button(onClick = onDismissExitoso) {
                            Text("Aceptar")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}