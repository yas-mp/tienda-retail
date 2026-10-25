package com.example.tiendaretailsur.model

data class MenuPrincipalUiState(
    val ultimoResumen: ResumenDiario? = null,
    val listaAccesos: List<AccesoTienda> = emptyList(),
    val totalIngresosCuatroPuertas: Int = 14520,
    val porcentajeTransito: Float = 62.4f,
    val porcentajeClientes: Float = 37.6f
)

data class AccesoTienda(
    val id: String,
    val nombre: String,
    val descripcion: String
)

data class ResumenDiario(
    val fecha: String,
    val diaSemana: String,
    val tipoDia: String,
    val a1: Int,
    val a2: Int,
    val a3: Int,
    val a4: Int,
    val totalIngresos: Int,
    val transacciones: Int,
    val conversion: Double,
    val observaciones: String = ""
)

data class ReporteHorario(
    val bloque: String,
    val a1: Int,
    val a2: Int,
    val a3: Int,
    val a4: Int,
    val total: Int,
    val boletas: Int,
    val conversion: Double
)



