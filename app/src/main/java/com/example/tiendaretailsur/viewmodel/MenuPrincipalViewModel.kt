package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.MenuPrincipalUiState
import com.example.tiendaretailsur.model.TiendaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuPrincipalViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        MenuPrincipalUiState(
            ultimoResumen = TiendaRepository.listaResumenesDiarios.lastOrNull(),
            listaAccesos = TiendaRepository.listaAccesos,
            totalIngresosCuatroPuertas = 14520,
            porcentajeTransito = 62.4f,
            porcentajeClientes = 37.6f
        )
    )
    val uiState: StateFlow<MenuPrincipalUiState> = _uiState.asStateFlow()
}
