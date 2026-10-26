package com.example.tiendaretailsur.navigation

sealed class Ruta(val ruta: String) {
    data object Login : Ruta("login")
    data object RecuperacionContrasena : Ruta("recuperacion_contrasena")
    data object MenuPrincipal : Ruta("menu_principal")
    data object FormularioConteo : Ruta("formulario_conteo")
    data object AdminPanel : Ruta("admin_panel")
    data object AjustesAdmin : Ruta("ajustes_admin")
    data object Perfil : Ruta("perfil")
    data object FormularioRegistro : Ruta("formulario_registro")

}