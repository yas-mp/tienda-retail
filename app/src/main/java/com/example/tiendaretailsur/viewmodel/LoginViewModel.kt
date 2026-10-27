package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val dbHelper: UsuarioDbHelper
) : ViewModel() {

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
        val correoOIdentificador = currentState.correo.trim()
        val contrasena = currentState.contrasena.trim()

        if (correoOIdentificador.isBlank()) {
            _uiState.update { it.copy(errorCorreo = "Ingresa tu correo o RUT") }
            return
        }
        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorContrasena = "La contraseña es requerida") }
            return
        }

        val usuario = dbHelper.autenticarUsuario(correoOIdentificador, contrasena)

        if (usuario != null) {
            _uiState.update {
                it.copy(
                    loginExitoso = true,
                    usuarioAutenticado = usuario,
                    errorCorreo = null,
                    errorContrasena = null,
                    mensajeExitoLogin = "Bienvenido/a ${usuario.nombreCompleto} (${usuario.perfil})"
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    loginExitoso = false,
                    errorContrasena = "Credenciales incorrectas o usuario no encontrado"
                )
            }
        }
    }
}