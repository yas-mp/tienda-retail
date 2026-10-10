package com.example.tiendaretailsur.model

data class FormularioRegistroUiState(
    val rut: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val telefono: String = "",
    val contrasena: String = "",
    val esActivo: Boolean = false,
    val aceptarTerminos: Boolean = false,

    val contrasenaVisible: Boolean = false,
    val errorFormatoRut: String? = null,
    val errorNombre: String? = null,
    val errorApellido: String? = null,
    val errorFormatoCorreo: String? = null,
    val errorTelefono: String? = null,
    val errorContrasena: String? = null,
    val errorTerminos: String? = null,

    val registroExitoso: Boolean = false
){

}