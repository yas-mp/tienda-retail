package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.AdminDashboardUiState
import com.example.tiendaretailsur.model.SesionResumen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdminDashboardViewModel(
    private val dbHelper: UsuarioDbHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminDashboardUiState())
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()

    init {
        cargarDatosDashboard()
    }

    private fun cargarDatosDashboard(){
        val listaConteos = dbHelper.listarConteos()

        val listaSesiones = listaConteos.mapIndexed { index, conteo ->
            SesionResumen(
                id = (index + 1).toLong(),
                observador = conteo.observador,
                hora = conteo.hora,
                conteo = conteo.cantidadClientes,
                estado = 1
            )
        }
        _uiState.update { estadoActual ->
            estadoActual.copy(
                totalVisitas = listaSesiones.sumOf { it.conteo },
                observadoresActivos = listaConteos.map { it.observador }.distinct().size,
                listaSesiones = listaSesiones,
                flujoPorHora = dbHelper.obtenerFlujoPorHora(),
                segmentacion = dbHelper.obtenerSegmentacion(),
                estaCargando = false
            )
        }

    }
}