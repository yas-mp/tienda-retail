package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.HashUtils
import com.example.tiendaretailsur.model.PasoRecuperacion
import com.example.tiendaretailsur.model.RecuperarContrasenaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecuperarContrasenaViewModel(
    private val dbHelper: UsuarioDbHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecuperarContrasenaUiState())
    val uiState: StateFlow<RecuperarContrasenaUiState> = _uiState.asStateFlow()

    fun onIdentificadorCambia(valor: String) {
        _uiState.update { it.copy(identificadorUsuario = valor, errorUsuario = null) }
    }

    fun onRespuesta1Cambia(valor: String) {
        _uiState.update { it.copy(respuesta1 = valor, errorRespuesta1 = null) }
    }

    fun onRespuesta2Cambia(valor: String) {
        _uiState.update { it.copy(respuesta2 = valor, errorRespuesta2 = null) }
    }

    fun onNuevaContrasenaCambia(valor: String) {
        _uiState.update { it.copy(nuevaContrasena = valor, errorNuevaContrasena = null) }
    }

    fun onConfirmarContrasenaCambia(valor: String) {
        _uiState.update { it.copy(confirmarContrasena = valor, errorConfirmarContrasena = null) }
    }

    fun onAlternarNuevaVisible() {
        _uiState.update { it.copy(nuevaContrasenaVisible = !it.nuevaContrasenaVisible) }
    }

    fun onAlternarConfirmarVisible() {
        _uiState.update { it.copy(confirmarContrasenaVisible = !it.confirmarContrasenaVisible) }
    }

    fun buscarUsuario() {
        val identificador = _uiState.value.identificadorUsuario.trim()
        if (identificador.isBlank()) {
            _uiState.update { it.copy(errorUsuario = "Ingresa tu correo electrónico o RUT") }
            return
        }

        val pregunta = dbHelper.obtenerPreguntaSeguridad(identificador)
        if (pregunta != null) {
            _uiState.update {
                it.copy(
                    pasoActual = PasoRecuperacion.PREGUNTAS_SEGURIDAD,
                    correoUsuarioEncontrado = identificador,
                    pregunta1 = pregunta,
                    errorUsuario = null
                )
            }
        } else {
            _uiState.update {
                it.copy(errorUsuario = "No se encontró ningún colaborador con ese correo o RUT")
            }
        }
    }

    fun verificarRespuestas() {
        val estado = _uiState.value
        val identificador = estado.correoUsuarioEncontrado.trim()
        val respuestaIngresada = estado.respuesta1.trim()

        if (respuestaIngresada.isBlank()) {
            _uiState.update { it.copy(errorRespuesta1 = "Ingresa tu respuesta de seguridad") }
            return
        }

        val esCorrecta = dbHelper.verificarRespuesta(identificador, respuestaIngresada)

        if (esCorrecta) {
            _uiState.update {
                it.copy(
                    pasoActual = PasoRecuperacion.CAMBIAR_CONTRASENA,
                    errorRespuesta1 = null
                )
            }
        } else {
            _uiState.update {
                it.copy(errorRespuesta1 = "Respuesta de seguridad incorrecta")
            }
        }
    }

    fun guardarNuevaContrasena() {
        val estado = _uiState.value
        val errorNueva = validarFormatoContrasena(estado.nuevaContrasena)
        val errorConfirmar = when {
            estado.confirmarContrasena.isBlank() -> "Debes confirmar la contraseña"
            estado.nuevaContrasena != estado.confirmarContrasena -> "Las contraseñas no coinciden"
            else -> null
        }

        if (errorNueva != null || errorConfirmar != null) {
            _uiState.update {
                it.copy(
                    errorNuevaContrasena = errorNueva,
                    errorConfirmarContrasena = errorConfirmar
                )
            }
            return
        }

        val identificador = estado.correoUsuarioEncontrado.trim()
        val nuevaClave = estado.nuevaContrasena.trim()

        val filasAfectadas = dbHelper.actualizarContrasena(identificador, nuevaClave)

        if (filasAfectadas > 0) {
            _uiState.update {
                it.copy(
                    pasoActual = PasoRecuperacion.COMPLETADO,
                    nuevoHashGenerado = HashUtils.generarHash(nuevaClave)
                )
            }
        } else {
            _uiState.update {
                it.copy(errorNuevaContrasena = "Error al actualizar la contraseña")
            }
        }
    }

    private fun validarFormatoContrasena(contrasena: String): String? {
        if (contrasena.length < 8) return "La contraseña debe tener al menos 8 caracteres"
        if (!contrasena.any { it.isUpperCase() }) return "Debe tener al menos una mayúscula"
        if (!contrasena.any { it.isLowerCase() }) return "Debe tener al menos una minúscula"
        if (!contrasena.any { it.isDigit() }) return "Debe tener al menos un número"
        return null
    }
}