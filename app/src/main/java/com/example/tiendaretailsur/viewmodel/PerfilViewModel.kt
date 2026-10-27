package com.example.tiendaretailsur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.Perfil
import com.example.tiendaretailsur.model.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class PerfilViewModel(
    private val dbHelper: UsuarioDbHelper
) : ViewModel() {
    private var correoUsuario: String = "admin@rincondelsur.cl"
    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarPerfil("admin@rincondelsur.cl")
    }

    fun cargarPerfil(correo: String) {
        if (correo.isBlank()) return
        correoUsuario = correo
        val usuario = try {
            dbHelper.obtenerUsuarioPorCorreo(correo)
        } catch (e: Exception) {
            null
        }

        val perfilTienda = if (usuario != null) {
            Perfil(
                nombre = usuario.nombreCompleto,
                correo = usuario.correo,
                turno = if (usuario.perfil in listOf("P-01", "P-02", "SUBGERENTE")) "N/A" else "Turno Diurno",
                cargo = usuario.perfil
            )
        } else {
            Perfil(
                nombre = "Gabriel Gómez",
                correo = correo,
                turno = "N/A",
                cargo = "SUBGERENTE"
            )
        }
        _uiState.update { it.copy(perfil = perfilTienda, avatarBytes = usuario?.avatar) }
    }

    fun setAvatarBytes(bytes: ByteArray?) {
        _uiState.update { it.copy(avatarBytes = bytes) }
        if (bytes != null) {
            dbHelper.actualizarAvatarPorCorreo(correoUsuario, bytes)
        }
    }
}
