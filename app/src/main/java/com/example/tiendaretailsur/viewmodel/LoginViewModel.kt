package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.LoginUiState
import com.example.tiendaretailsur.model.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onCorreoChanged(correo: String) {
        _uiState.update {
            it.copy(correo = correo, errorCorreo = null, mensajeExitoLogin = null)
        }
    }

    fun onContrasenaChanged(contrasena: String) {
        _uiState.update {
            it.copy(contrasena = contrasena, errorContrasena = null, mensajeExitoLogin = null)
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun login() {
        val currentState = _uiState.value
        if (currentState.correo.isBlank()) {
            _uiState.update { it.copy(errorCorreo = "Ingresa tu correo o RUT") }
            return
        }
        if (currentState.contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "La contraseña es requerida") }
            return
        }

        val resultado = UsuarioRepository.autenticar(
            identificador = currentState.correo,
            contrasenaPlana = currentState.contrasena
        )

        resultado.fold(
            onSuccess = { usuario ->
                _uiState.update {
                    it.copy(
                        loginExitoso = true,
                        usuarioAutenticado = usuario,
                        errorCorreo = null,
                        errorContrasena = null,
                        mensajeExitoLogin = "Bienvenido/a ${usuario.nombreCompleto} (${usuario.perfil})"
                    )
                }
            },
            onFailure = { error ->
                _uiState.update {
                    it.copy(
                        loginExitoso = false,
                        errorContrasena = error.message
                    )
                }
            }
        )
    }
}