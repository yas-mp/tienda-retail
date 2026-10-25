package com.example.tiendaretailsur.model

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

data class MenuPrincipalUiState(
    val ultimoResumen: ResumenDiario? = null,
    val listaAccesos: List<AccesoTienda> = emptyList(),
    val totalIngresosCuatroPuertas: Int = 0,
    val porcentajeTransito: Float = 0f,
    val porcentajeClientes: Float = 0f,
    val conteosPorPuerta: Map<String, Int> = emptyMap(),
    val avatarBytes: ByteArray? = null

)



