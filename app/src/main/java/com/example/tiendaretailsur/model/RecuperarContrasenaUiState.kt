package com.example.tiendaretailsur.model

enum class PasoRecuperacion {
    BUSCAR_USUARIO,
    PREGUNTAS_SEGURIDAD,
    CAMBIAR_CONTRASENA,
    COMPLETADO
}

data class RecuperarContrasenaUiState(
    val pasoActual: PasoRecuperacion = PasoRecuperacion.BUSCAR_USUARIO,
    val identificadorUsuario: String = "",
    val nombreUsuarioEncontrado: String = "",
    val correoUsuarioEncontrado: String = "",

    val pregunta1: String = "",
    val pregunta2: String = "",
    val respuesta1: String = "",
    val respuesta2: String = "",

    val nuevaContrasena: String = "",
    val confirmarContrasena: String = "",
    val nuevaContrasenaVisible: Boolean = false,
    val confirmarContrasenaVisible: Boolean = false,
    val nuevoHashGenerado: String = "",

    val errorUsuario: String? = null,
    val errorRespuesta1: String? = null,
    val errorRespuesta2: String? = null,
    val errorNuevaContrasena: String? = null,
    val errorConfirmarContrasena: String? = null
)