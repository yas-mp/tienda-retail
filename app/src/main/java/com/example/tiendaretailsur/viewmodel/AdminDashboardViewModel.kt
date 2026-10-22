package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.GraficoRepository
import com.example.tiendaretailsur.model.AdminDashboardUiState
import com.example.tiendaretailsur.model.SesionResumen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdminDashboardViewModel(
    private val graficoRepository: GraficoRepository = GraficoRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminDashboardUiState())
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()

    init {
        cargarDatosPrueba()
    }

    private fun cargarDatosPrueba(){
        val listaSesionesPrueba = listOf(
            SesionResumen(
                id = 1,
                observador = "Juan Pérez",
                hora = "13:30",
                conteo = 45,
                estado = 1
            ),
            SesionResumen(
                id = 2,
                observador = "Carlos Rodriguez",
                hora = "17:30",
                conteo = 20,
                estado = 0
            ),
            SesionResumen(
                id = 3,
                observador = "Ana Morales",
                hora = "11:00 hrs",
                conteo = 60,
                estado = 1
            )
        )
        _uiState.update { estadoActual ->
            estadoActual.copy(
                totalVisitas = listaSesionesPrueba.sumOf { it.conteo },
                observadoresActivos = listaSesionesPrueba.count{it.estado == 0},
                listaSesiones = listaSesionesPrueba,
                flujoPorHora = graficoRepository.obtenerFlujoPorHora(),
                estaCargando = false
            )
        }

    }
}