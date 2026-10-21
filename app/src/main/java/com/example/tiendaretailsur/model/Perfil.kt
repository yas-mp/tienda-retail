package com.example.tiendaretailsur.model

data class Perfil(
    val nombre: String = "fulanito",
    val correo: String = "fulanito@tiendaretailsur.cl",
    val turno: String = "Turno Diurno",
    val cargo: String = "Subgerente Comercial"
)

data class PerfilUiState(
    val perfil: Perfil = Perfil(),
    val avatarBytes: ByteArray? = null
)
