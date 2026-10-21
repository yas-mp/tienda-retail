package com.example.tiendaretailsur.model

data class Usuario(
    val codigo: String = "",
    val nombreCompleto: String = "",
    val rut: String = "",
    val cargo: String = "",
    val perfil: String = "P-04",
    val correo: String = "",
    val telefono: String = "",
    val passwordHash: String = "",
    val activo: Boolean = true,
    val preguntaSeguridad1: String = "",
    val respuestaHash1: String = "",
    val preguntaSeguridad2: String = "",
    val respuestaHash2: String = ""
) {
    val nombre: String
        get() = nombreCompleto
}