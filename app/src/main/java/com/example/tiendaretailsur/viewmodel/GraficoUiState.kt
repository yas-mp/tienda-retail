package com.example.tiendaretailsur.viewmodel

import com.example.tiendaretailsur.model.DatoGrafico

data class GraficosUiState(
    val flujoPorHora: List<DatoGrafico> = emptyList(),
    val segmentacion: List<DatoGrafico> = emptyList(),
    val estaCargando: Boolean = true
)