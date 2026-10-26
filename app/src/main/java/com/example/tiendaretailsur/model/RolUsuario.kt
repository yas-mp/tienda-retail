package com.example.tiendaretailsur.model

enum class RolUsuario(
    val id: Long,
    val codigo: String,
    val nombre: String,
    val descripcion: String
) {
    SUBGERENTE(1, "P-02", "Subgerente", "Subgerente comercial"),
    OBSERVADOR(2, "P-04", "Observador", "Observador de terreno"),
    JEFA_TIENDA(3, "P-01", "Jefa de tienda", "Jefa de tienda"),
    SUPERVISOR_PISO(4, "P-03", "Supervisor de piso", "Supervisor de piso");

    companion object {
        fun buscarId(id: Long): RolUsuario {
            return entries.find { it.id == id } ?: OBSERVADOR
        }

        fun obtenerListaRoles(): List<RolUsuario> {
            return entries.toList()
        }
    }
}