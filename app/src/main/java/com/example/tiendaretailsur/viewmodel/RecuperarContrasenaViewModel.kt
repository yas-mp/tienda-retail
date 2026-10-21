package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.HashUtils
import com.example.tiendaretailsur.model.PasoRecuperacion
import com.example.tiendaretailsur.model.RecuperarContrasenaUiState
import com.example.tiendaretailsur.model.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecuperarContrasenaViewModel : ViewModel() {

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

    // Busca usuario por Correo o RUT
    fun buscarUsuario() {
        val identificador = _uiState.value.identificadorUsuario
        if (identificador.isBlank()) {
            _uiState.update { it.copy(errorUsuario = "Ingresa tu correo electrónico o RUT") }
            return
        }

        val usuario = UsuarioRepository.buscarPorCorreoORut(identificador)
        if (usuario == null) {
            _uiState.update {
                it.copy(errorUsuario = "No se encontró ningún colaborador con ese correo o RUT")
            }
            return
        }

        if (!usuario.activo) {
            _uiState.update {
                it.copy(errorUsuario = "La cuenta de este colaborador está desactivada")
            }
            return
        }

        _uiState.update {
            it.copy(
                pasoActual = PasoRecuperacion.PREGUNTAS_SEGURIDAD,
                nombreUsuarioEncontrado = usuario.nombreCompleto,
                correoUsuarioEncontrado = usuario.correo,
                pregunta1 = usuario.preguntaSeguridad1,
                pregunta2 = usuario.preguntaSeguridad2,
                errorUsuario = null
            )
        }
    }

    // Verificar las preguntas de seguridad mediante Hash
    fun verificarRespuestas() {
        val estado = _uiState.value
        val usuario = UsuarioRepository.buscarPorCorreoORut(estado.correoUsuarioEncontrado) ?: return

        val r1Correcta = HashUtils.generarHashRespuesta(estado.respuesta1) == usuario.respuestaHash1
        val r2Correcta = HashUtils.generarHashRespuesta(estado.respuesta2) == usuario.respuestaHash2

        val error1 = if (!r1Correcta) "Respuesta incorrecta" else null
        val error2 = if (!r2Correcta) "Respuesta incorrecta" else null

        if (r1Correcta && r2Correcta) {
            _uiState.update {
                it.copy(
                    pasoActual = PasoRecuperacion.CAMBIAR_CONTRASENA,
                    errorRespuesta1 = null,
                    errorRespuesta2 = null
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    errorRespuesta1 = error1,
                    errorRespuesta2 = error2
                )
            }
        }
    }

    // Validar y encriptar (Hash SHA-256) la nueva contraseña
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

        val actualizado = UsuarioRepository.actualizarContrasena(
            correo = estado.correoUsuarioEncontrado,
            nuevaContrasenaPlana = estado.nuevaContrasena
        )

        if (actualizado) {
            _uiState.update {
                it.copy(
                    pasoActual = PasoRecuperacion.COMPLETADO,
                    nuevoHashGenerado = HashUtils.generarHash(estado.nuevaContrasena)
                )
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