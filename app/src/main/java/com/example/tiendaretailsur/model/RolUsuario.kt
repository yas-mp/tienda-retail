package com.example.tiendaretailsur.model

enum class RolUsuario(val codigo: String, val etiqueta: String) {
    JEFA_TIENDA("P-01", "Jefa de tienda - Supervisión e indicadores"),
    SUBGERENTE_COMERCIAL("P-02", "Encargado de los reportes y conteos diarios"),
    SUPERVISOR_PISO("P-03", "Supervisor/a de piso - Programa y valida sesiones"),
    OBSERVADOR("P-04", "Vendedor de piso - Registra conteos de pasillo");

    companion object {
        fun desdeCodigo(codigo: String): RolUsuario {
            return entries.find { it.codigo.equals(codigo, ignoreCase = true) } ?: OBSERVADOR
        }
    }
}