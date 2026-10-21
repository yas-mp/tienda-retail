package com.example.tiendaretailsur.model

data class AdminDashboardUiState(
    val totalVisitas: Int = 0,
    val observadoresActivos: Int = 0,
    val listaSesiones: List<SesionResumen> = emptyList(),
    val estaCargando: Boolean = false
) {
    fun flujoPorHora(): List<DatoGrafico> {
        val totalPorHora = listaSesiones
            .groupBy { it.hora.substringBefore(":").trim().toIntOrNull() ?: 0 }
            .mapValues { (_, sesiones) -> sesiones.sumOf { it.conteo } }

        val maximo = totalPorHora.values.maxOrNull() ?: return emptyList()
        if (maximo == 0) return emptyList()

        return totalPorHora.toSortedMap().map { (hora, total) ->
            DatoGrafico(etiqueta = "${hora}h", valor = total * 100 / maximo)
        }
    }
}