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


class PerfilViewModel : AndroidViewModel {
    private val dbHelper: UsuarioDbHelper?
    private var userId: Long = 1L

    constructor() : super(Application()) {
        dbHelper = null
    }

    constructor(application: Application) : super(application) {
        dbHelper = UsuarioDbHelper(application)
    }

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarPerfil()
    }

    fun cargarPerfil(idUsuario: Long = 1L) {
        userId = idUsuario
        val perfilTienda = Perfil(
            nombre = if (idUsuario == 1L) "Andrea Valencia" else "Fulanito Pérez",
            correo = if (idUsuario == 1L) "andrea.valencia@tiendaretailsur.cl" else "fulanito@tiendaretailsur.cl",
            turno = if (idUsuario == 1L) "N/A" else "Turno Vespertino",
            cargo = if (idUsuario == 1L) "Subgerente Comercial" else "Colaborador de Aforo"
        )
        _uiState.update { it.copy(perfil = perfilTienda) }
    }

    fun setAvatarBytes(bytes: ByteArray?) {
        _uiState.update { it.copy(avatarBytes = bytes) }
        if (bytes != null) {
            dbHelper?.actualizarAvatar(userId, bytes)
        }
    }
}
