package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.Perfil
import com.example.tiendaretailsur.model.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel : ViewModel() {
    private val perfilTienda = Perfil(
        nombre = "fulanito",
        correo = "fulanito@tiendaretailsur.cl",
        turno = "Turno Diurno",
        cargo = "Subgerente Comercial"
    )

    private val _uiState = MutableStateFlow(PerfilUiState(perfil = perfilTienda))
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun setAvatarBytes(bytes: ByteArray?) {
        _uiState.update { it.copy(avatarBytes = bytes) }
    }
}
