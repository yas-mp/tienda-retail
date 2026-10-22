package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.GraficoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GraficosViewModel(
    private val graficoRepository: GraficoRepository = GraficoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GraficosUiState())
    val uiState: StateFlow<GraficosUiState> = _uiState.asStateFlow()

    init {
        cargarDatosGrafico()
    }

    private fun cargarDatosGrafico() {
        _uiState.update { estadoActual ->
            estadoActual.copy(
                flujoPorHora = graficoRepository.obtenerFlujoPorHora(),
                segmentacion = graficoRepository.obtenerSegmentacion(),
                estaCargando = false
            )
        }
    }

    fun recargarDatos() {
        _uiState.update { estadoActual ->
            estadoActual.copy(
                flujoPorHora = graficoRepository.actualizarDatos()
            )
        }
    }
}