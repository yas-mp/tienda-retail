package com.example.tiendaretailsur.data

import com.example.tiendaretailsur.model.DatoGrafico
import kotlin.random.Random

class GraficoRepository {
    private val flujoPorHora = listOf(
        DatoGrafico("10h", 25),
        DatoGrafico("12h", 65),
        DatoGrafico("14h", 45),
        DatoGrafico("16h", 30),
        DatoGrafico("18h", 80)
    )

    // 2. Datos para la "Segmentación de flujo"
    private val segmentacionFlujo = listOf(
        DatoGrafico("Tránsito (Pasillo)", 54),
        DatoGrafico("Clientes / Visita", 46)
    )

    fun obtenerFlujoPorHora(): List<DatoGrafico> = flujoPorHora

    fun obtenerSegmentacion(): List<DatoGrafico> = segmentacionFlujo

    // Para cuando quieras simular que los datos cambian
    fun actualizarDatos(): List<DatoGrafico> =
        flujoPorHora.map { it.copy(valor = Random.nextInt(15, 95)) }
}