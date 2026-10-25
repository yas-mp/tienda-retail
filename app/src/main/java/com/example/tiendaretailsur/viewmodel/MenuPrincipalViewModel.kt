package com.example.tiendaretailsur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.MenuPrincipalUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class MenuPrincipalViewModel : AndroidViewModel {
    private val dbHelper: UsuarioDbHelper?

    constructor() : super(Application()) {
        dbHelper = null
    }

    constructor(application: Application) : super(application) {
        dbHelper = UsuarioDbHelper(application)
    }

    private val _uiState = MutableStateFlow(MenuPrincipalUiState())
    val uiState: StateFlow<MenuPrincipalUiState> = _uiState.asStateFlow()

    init {
        cargarDatosDesdeDb()
    }

    fun cargarDatosDesdeDb() {
        val conteos = try {
            dbHelper?.listarConteos() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val puertas = try {
            dbHelper?.listarPuertas() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
        val listaAccesosFinal = if (puertas.isNotEmpty()) puertas else emptyList()

        if (conteos.isEmpty() && dbHelper == null) {
            _uiState.update {
                it.copy(
                    ultimoResumen = null,
                    listaAccesos = listOf(
                        com.example.tiendaretailsur.model.AccesoTienda("A1", "Acceso Principal", "Patio de comidas"),
                        com.example.tiendaretailsur.model.AccesoTienda("A2", "Acceso Lateral", "Pasillo norte"),
                        com.example.tiendaretailsur.model.AccesoTienda("A3", "Acceso Estacionamiento", "Subterráneo"),
                        com.example.tiendaretailsur.model.AccesoTienda("A4", "Acceso Conexión", "Tienda vecina")
                    ),
                    totalIngresosCuatroPuertas = 470,
                    porcentajeTransito = 60.0f,
                    porcentajeClientes = 40.0f,
                    conteosPorPuerta = mapOf("A1" to 205, "A2" to 115, "A3" to 68, "A4" to 82)
                )
            }
            return
        }

        val totalTransito = conteos.sumOf { it.cantidadTransito }
        val totalClientes = conteos.sumOf { it.cantidadClientes }
        val totalIngresos = totalTransito + totalClientes

        val porcentajeT = if (totalIngresos > 0) (totalTransito * 100f) / totalIngresos else 0f
        val porcentajeC = if (totalIngresos > 0) (totalClientes * 100f) / totalIngresos else 0f

        val conteosPorPuerta = conteos.groupBy { it.puertaAcceso }
            .mapValues { (_, lista) -> lista.sumOf { it.cantidadTransito + it.cantidadClientes } }

        _uiState.update {
            it.copy(
                ultimoResumen = null,
                listaAccesos = listaAccesosFinal,
                totalIngresosCuatroPuertas = totalIngresos,
                porcentajeTransito = String.format(Locale.getDefault(), "%.1f", porcentajeT).toFloat(),
                porcentajeClientes = String.format(Locale.getDefault(), "%.1f", porcentajeC).toFloat(),
                conteosPorPuerta = conteosPorPuerta
            )
        }
    }
}
