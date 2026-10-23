package com.example.tiendaretailsur.model

enum class SeccionAjustesAdmin(val titulo: String) {
    COLABORADORES("Colaboradores"),
    PERFILES("Perfiles App"),
    TIENDA("Tienda y Cuenta")
}

data class AjustesAdminUiState(
    val seccionActual: SeccionAjustesAdmin = SeccionAjustesAdmin.COLABORADORES,
    val busqueda: String = "",
    val filtroPerfil: String = "TODOS",

    val usuarios: List<Usuario> = emptyList(),
    val perfiles: List<ConfiguracionPerfil> = emptyList(),
    val accesos: List<ConfiguracionAcceso> = emptyList(),
    val metaConversionTexto: String = "10.0",

    // Estado del modal de edición de colaborador
    val usuarioEnEdicion: Usuario? = null,
    val editNombre: String = "",
    val editRut: String = "",
    val editCorreo: String = "",
    val editTelefono: String = "",
    val editCargo: String = "",
    val editPerfil: String = "P-04",
    val editActivo: Boolean = true,
    val editNuevaContrasena: String = "",
    val errorEdicionUsuario: String? = null,

    // Estado del modal de edición de perfil de la app (P-01 a P-04)
    val perfilEnEdicion: ConfiguracionPerfil? = null,
    val editPerfilNombre: String = "",
    val editPerfilDescripcion: String = "",
    val editPerfilHabilitado: Boolean = true,
    val aplicarEstadoATodosLosUsuariosDelPerfil: Boolean = false,

    val mensajeConfirmacion: String? = null
)