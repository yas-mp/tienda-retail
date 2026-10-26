package com.example.tiendaretailsur.model

data class ConfiguracionPerfil(
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val habilitado: Boolean = true,
    val permisosActivos: List<String> = emptyList()
)

data class ConfiguracionAcceso(
    val codigo: String,
    val nombre: String,
    val ubicacion: String = "",
    val descripcion: String = "",
    val habilitado: Boolean = true,
    val modoOfflinePrioritario: Boolean = false
)