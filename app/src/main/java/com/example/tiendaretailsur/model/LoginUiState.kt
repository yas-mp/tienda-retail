package com.example.tiendaretailsur.model

data class LoginUiState (
    val correo: String = "",
    val contrasena: String = "",
    val errorCorreo: String? = null,
    val errorContrasena: String? = null,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false
)