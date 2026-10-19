package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.FormularioConteoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularioConteoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormularioConteoUiState())
    val uiState: StateFlow<FormularioConteoUiState> = _uiState.asStateFlow()

    fun setPuerta(puerta: String) {
        _uiState.update { it.copy(puertaAcceso = puerta, envioExitoso = false) }
    }

    fun incrementarTransito() {
        _uiState.update { it.copy(cantidadTransito = it.cantidadTransito + 1, envioExitoso = false) }
    }

    fun decrementarTransito() {
        _uiState.update { it.copy(cantidadTransito = maxOf(0, it.cantidadTransito - 1), envioExitoso = false) }
    }

    fun incrementarClientes() {
        _uiState.update { it.copy(cantidadClientes = it.cantidadClientes + 1, envioExitoso = false) }
    }

    fun decrementarClientes() {
        _uiState.update { it.copy(cantidadClientes = maxOf(0, it.cantidadClientes - 1), envioExitoso = false) }
    }

    fun seleccionarRangoEtario(rango: String) {
        _uiState.update { it.copy(rangoEtario = rango, envioExitoso = false) }
    }

    fun seleccionarCompania(comp: String) {
        _uiState.update { it.copy(compania = comp, envioExitoso = false) }
    }

    fun enviarFormulario() {
        _uiState.update { it.copy(envioExitoso = true) }
    }

    fun limpiarFormulario() {
        _uiState.update { FormularioConteoUiState(puertaAcceso = it.puertaAcceso) }
    }
}
