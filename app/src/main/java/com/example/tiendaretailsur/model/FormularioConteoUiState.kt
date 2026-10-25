package com.example.tiendaretailsur.model

data class FormularioConteoUiState(
    val puertaAcceso: String = "A1",
    val cantidadTransito: Int = 0,
    val cantidadClientes: Int = 0,
    val rangoEtario: String = "26–40",
    val compania: String = "Solo",
    val observador: String = "",
    val hora: String = "",
    val envioExitoso: Boolean = false
)
