package com.example.tiendaretailsur.model

data class AdminDashboardUiState(
    val totalVisitas: Int = 0,
    val observadoresActivos: Int = 0,
    val flujoPorHora: List<DatoGrafico> = emptyList(),
    val segmentacion: List<DatoGrafico> = emptyList(),
    val listaSesiones: List<SesionResumen> = emptyList(),
    val estaCargando: Boolean = false
) {

}