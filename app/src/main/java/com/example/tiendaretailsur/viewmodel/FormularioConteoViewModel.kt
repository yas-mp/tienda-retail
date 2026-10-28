package com.example.tiendaretailsur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.AccesoTienda
import com.example.tiendaretailsur.model.FormularioConteoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularioConteoViewModel : AndroidViewModel {
    private val dbHelper: UsuarioDbHelper?

    constructor() : super(Application()) {
        dbHelper = null
    }

    constructor(application: Application) : super(application) {
        dbHelper = UsuarioDbHelper(application)
        cargarPuertasHabilitadas()
    }

    private val _uiState = MutableStateFlow(FormularioConteoUiState())
    val uiState: StateFlow<FormularioConteoUiState> = _uiState.asStateFlow()

    fun cargarPuertasHabilitadas() {
        val db = dbHelper ?: return
        val accesosHabilitados = try {
            db.listarPuertas().filter { it.estaHabilitado }
        } catch (e: Exception) {
            emptyList()
        }

        _uiState.update { estado ->
            val puertaValida = if (accesosHabilitados.any { it.nombre.equals(estado.puertaAcceso, ignoreCase = true) || it.id.equals(estado.puertaAcceso, ignoreCase = true) }) {
                estado.puertaAcceso
            } else {
                accesosHabilitados.firstOrNull()?.id ?: accesosHabilitados.firstOrNull()?.nombre ?: ""
            }

            estado.copy(puertaAcceso = puertaValida)
        }
    }

    fun obtenerPuertasHabilitadas(): List<AccesoTienda> {
        return try {
            dbHelper?.listarPuertas()?.filter { it.estaHabilitado } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun estaPuertaActualBloqueada(): Boolean {
        val db = dbHelper ?: return false
        val puertaActual = _uiState.value.puertaAcceso
        if (puertaActual.isBlank()) return false

        val puertas = try { db.listarPuertas() } catch (e: Exception) { emptyList() }
        val puertaEncontrada = puertas.find {
            it.id.equals(puertaActual, ignoreCase = true) || it.nombre.equals(puertaActual, ignoreCase = true)
        }

        return puertaEncontrada != null && !puertaEncontrada.estaHabilitado
    }

    fun setPuerta(puerta: String) {
        _uiState.update { it.copy(puertaAcceso = puerta, envioExitoso = false) }
    }

    fun incrementarTransito() {
        if (estaPuertaActualBloqueada()) return
        _uiState.update { it.copy(cantidadTransito = it.cantidadTransito + 1, envioExitoso = false) }
    }

    fun decrementarTransito() {
        if (estaPuertaActualBloqueada()) return
        _uiState.update { it.copy(cantidadTransito = maxOf(0, it.cantidadTransito - 1), envioExitoso = false) }
    }

    fun incrementarClientes() {
        if (estaPuertaActualBloqueada()) return
        _uiState.update { it.copy(cantidadClientes = it.cantidadClientes + 1, envioExitoso = false) }
    }

    fun decrementarClientes() {
        if (estaPuertaActualBloqueada()) return
        _uiState.update { it.copy(cantidadClientes = maxOf(0, it.cantidadClientes - 1), envioExitoso = false) }
    }

    fun seleccionarRangoEtario(rango: String) {
        if (estaPuertaActualBloqueada()) return
        _uiState.update { it.copy(rangoEtario = rango, envioExitoso = false) }
    }

    fun seleccionarCompania(comp: String) {
        if (estaPuertaActualBloqueada()) return
        _uiState.update { it.copy(compania = comp, envioExitoso = false) }
    }

    fun enviarFormulario() {
        if (estaPuertaActualBloqueada()) {
            _uiState.update { it.copy(envioExitoso = false) }
            cargarPuertasHabilitadas()
            return
        }

        val estado = _uiState.value
        val db = dbHelper ?: return

        db.registrarFormularioConteo(estado, "Andrea Valencia", "12h")
        _uiState.update { FormularioConteoUiState(puertaAcceso = it.puertaAcceso, envioExitoso = true) }
    }

    fun limpiarExitoso() {
        _uiState.update { it.copy(envioExitoso = false) }
    }
}