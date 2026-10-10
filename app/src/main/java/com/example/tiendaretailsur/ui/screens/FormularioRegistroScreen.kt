package com.example.tiendaretailsur.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tiendaretailsur.viewmodel.FormularioRegistroViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaretailsur.R
import com.example.tiendaretailsur.model.FormularioRegistroUiState


@Composable
fun FormularioRegistroScreen(
    viewModel: FormularioRegistroViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FormularioRegistroContent(
        uiState = uiState,
        onRutCambia = viewModel::onRutCambia,
        onNombreCambia = viewModel::onNombreCambia,
        onApellidoCambia = viewModel::onApellidoCambia,
        onCorreoCambia = viewModel::onCorreoCambia,
        onTelefonoCambia = viewModel::onTelefonoCambia,
        onContrasenaCambia = viewModel::onContrasenaCambia,
        onAlternarContrasenaVisible = viewModel::onAlternarContrasenaVisible,
        onTerminosCambia = viewModel::onTerminosCambia,
        onLimpiarFormulario = viewModel::onLimpiarFormulario,
        onRegistrar = viewModel::onRegistrar
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistroContent(
    uiState: FormularioRegistroUiState,
    onRutCambia: (String) -> Unit,
    onNombreCambia: (String) -> Unit,
    onApellidoCambia: (String) -> Unit,
    onCorreoCambia: (String) -> Unit,
    onTelefonoCambia: (String) -> Unit,
    onContrasenaCambia: (String) -> Unit,
    onAlternarContrasenaVisible: () -> Unit,
    onTerminosCambia: (Boolean) -> Unit,
    onLimpiarFormulario: () -> Unit,
    onRegistrar: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(stringResource(R.string.titulo_registro))},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

        }

    }

}