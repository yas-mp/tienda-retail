package com.example.tiendaretailsur.model

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val errorCorreo: String? = null,
    val errorContrasena: String? = null,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val loginExitoso: Boolean = false,
    val usuarioAutenticado: Usuario? = null,
    val mensajeExitoLogin: String? = null
)