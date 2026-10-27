package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.AjustesAdminUiState
import com.example.tiendaretailsur.model.ConfiguracionAcceso
import com.example.tiendaretailsur.model.ConfiguracionPerfil
import com.example.tiendaretailsur.model.SeccionAjustesAdmin
import com.example.tiendaretailsur.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AjustesAdminViewModel(
    private val dbHelper: UsuarioDbHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(AjustesAdminUiState())
    val uiState: StateFlow<AjustesAdminUiState> = _uiState.asStateFlow()

    init {
        recargarDatos()
    }

    fun recargarDatos() {
        val usuariosBd = try {
            dbHelper.listarUsuarios()
        } catch (e: Exception) {
            emptyList()
        }

        val perfilesBd = try {
            dbHelper.listarPerfiles()
        } catch (e: Exception) {
            emptyList()
        }

        val puertasBd = try {
            dbHelper.listarPuertas()
        } catch (e: Exception) {
            emptyList()
        }

        val accesosConfig = puertasBd.map { puerta ->
            ConfiguracionAcceso(
                codigo = puerta.id,
                nombre = puerta.nombre,
                ubicacion = puerta.descripcion,
                habilitado = true
            )
        }

        _uiState.update {
            it.copy(
                usuarios = usuariosBd,
                perfiles = perfilesBd,
                accesos = accesosConfig,
                metaConversionTexto = "15.0"
            )
        }
    }

    fun seleccionarSeccion(seccion: SeccionAjustesAdmin) {
        _uiState.update { it.copy(seccionActual = seccion, mensajeConfirmacion = null) }
    }

    fun onBusquedaCambia(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun onFiltroPerfilCambia(codigoPerfil: String) {
        _uiState.update { it.copy(filtroPerfil = codigoPerfil) }
    }

    fun limpiarMensaje() {
        _uiState.update { it.copy(mensajeConfirmacion = null) }
    }
    fun alternarEstadoUsuario(usuario: Usuario, nuevoEstado: Boolean) {
        val estadoTexto = if (nuevoEstado) "habilitado" else "deshabilitado"

        _uiState.update { estadoActual ->
            val listaActualizada = estadoActual.usuarios.map { u ->
                if (u.rut == usuario.rut) {
                    u.copy(activo = nuevoEstado)
                } else {
                    u
                }
            }
            estadoActual.copy(
                usuarios = listaActualizada,
                mensajeConfirmacion = "Colaborador ${usuario.nombreCompleto} $estadoTexto"
            )
        }
    }
    fun abrirEditorUsuario(usuario: Usuario) {
        _uiState.update {
            it.copy(
                usuarioEnEdicion = usuario,
                editNombre = usuario.nombreCompleto,
                editRut = usuario.rut,
                editCorreo = usuario.correo,
                editTelefono = usuario.telefono,
                editCargo = usuario.cargo,
                editPerfil = usuario.perfil,
                editActivo = usuario.activo,
                editNuevaContrasena = "",
                errorEdicionUsuario = null
            )
        }
    }

    fun cerrarEditorUsuario() {
        _uiState.update { it.copy(usuarioEnEdicion = null, errorEdicionUsuario = null) }
    }

    fun onEditNombreCambia(valor: String) {
        _uiState.update { it.copy(editNombre = valor, errorEdicionUsuario = null) }
    }

    fun onEditRutCambia(valor: String) {
        _uiState.update { it.copy(editRut = valor, errorEdicionUsuario = null) }
    }

    fun onEditCorreoCambia(valor: String) {
        _uiState.update { it.copy(editCorreo = valor, errorEdicionUsuario = null) }
    }

    fun onEditTelefonoCambia(valor: String) {
        val limpio = valor.filter { it.isDigit() }.take(9)
        _uiState.update { it.copy(editTelefono = limpio, errorEdicionUsuario = null) }
    }

    fun onEditCargoCambia(valor: String) {
        _uiState.update { it.copy(editCargo = valor, errorEdicionUsuario = null) }
    }

    fun onEditPerfilCambia(codigoPerfil: String) {
        _uiState.update { it.copy(editPerfil = codigoPerfil, errorEdicionUsuario = null) }
    }

    fun onEditActivoCambia(activo: Boolean) {
        _uiState.update { it.copy(editActivo = activo) }
    }

    fun onEditNuevaContrasenaCambia(valor: String) {
        _uiState.update { it.copy(editNuevaContrasena = valor, errorEdicionUsuario = null) }
    }

    fun guardarCambiosUsuario() {
        val estado = _uiState.value
        val usuarioOriginal = estado.usuarioEnEdicion ?: return

        if (estado.editNombre.isBlank()) {
            _uiState.update { it.copy(errorEdicionUsuario = "El nombre completo no puede estar vacío") }
            return
        }
        if (!estado.editCorreo.contains("@") || !estado.editCorreo.contains(".")) {
            _uiState.update { it.copy(errorEdicionUsuario = "Ingresa un correo electrónico válido") }
            return
        }
        if (estado.editTelefono.length < 8) {
            _uiState.update { it.copy(errorEdicionUsuario = "El teléfono debe tener al menos 8 dígitos") }
            return
        }
        if (estado.editNuevaContrasena.isNotBlank() && estado.editNuevaContrasena.length < 4) {
            _uiState.update { it.copy(errorEdicionUsuario = "La nueva contraseña debe tener mínimo 4 caracteres") }
            return
        }

        try {
            dbHelper.actualizarUsuario(
                rut = usuarioOriginal.rut,
                nombre = estado.editNombre,
                correo = estado.editCorreo,
                telefono = estado.editTelefono
            )
            if (estado.editNuevaContrasena.isNotBlank()) {
                dbHelper.actualizarContrasena(
                    rutOCorreo = usuarioOriginal.rut,
                    nuevaContrasena = estado.editNuevaContrasena
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        recargarDatos()

        _uiState.update {
            it.copy(
                usuarioEnEdicion = null,
                errorEdicionUsuario = null,
                mensajeConfirmacion = "Datos de ${estado.editNombre} actualizados correctamente"
            )
        }
    }
    fun alternarEstadoPerfil(perfil: ConfiguracionPerfil, habilitado: Boolean) {
        val textoEstado = if (habilitado) "habilitado" else "deshabilitado"
        _uiState.update { estadoActual ->
            val listaPerfilesActualizada = estadoActual.perfiles.map { p ->
                if (p.codigo == perfil.codigo) {
                    p.copy(habilitado = habilitado)
                } else {
                    p
                }
            }
            estadoActual.copy(
                perfiles = listaPerfilesActualizada,
                mensajeConfirmacion = "Perfil ${perfil.codigo} (${perfil.nombre}) $textoEstado"
            )
        }
    }

    fun abrirEditorPerfil(perfil: ConfiguracionPerfil) {
        _uiState.update {
            it.copy(
                perfilEnEdicion = perfil,
                editPerfilNombre = perfil.nombre,
                editPerfilDescripcion = perfil.descripcion,
                editPerfilHabilitado = perfil.habilitado,
                aplicarEstadoATodosLosUsuariosDelPerfil = false
            )
        }
    }

    fun cerrarEditorPerfil() {
        _uiState.update { it.copy(perfilEnEdicion = null) }
    }

    fun onEditPerfilNombreCambia(valor: String) {
        _uiState.update { it.copy(editPerfilNombre = valor) }
    }

    fun onEditPerfilDescripcionCambia(valor: String) {
        _uiState.update { it.copy(editPerfilDescripcion = valor) }
    }

    fun onEditPerfilHabilitadoCambia(valor: Boolean) {
        _uiState.update { it.copy(editPerfilHabilitado = valor) }
    }

    fun onAplicarATodosCambia(valor: Boolean) {
        _uiState.update { it.copy(aplicarEstadoATodosLosUsuariosDelPerfil = valor) }
    }

    fun guardarCambiosPerfil() {
        val estado = _uiState.value
        val perfil = estado.perfilEnEdicion ?: return
        if (estado.editPerfilNombre.isBlank()) return

        _uiState.update {
            it.copy(
                perfilEnEdicion = null,
                mensajeConfirmacion = "Configuración del perfil ${perfil.codigo} guardada"
            )
        }
    }

    // Ajustes de Tienda
    fun onMetaConversionCambia(valor: String) {
        _uiState.update { it.copy(metaConversionTexto = valor) }
    }

    fun guardarMetaConversion() {
        val valorNum = _uiState.value.metaConversionTexto.replace(",", ".").toDoubleOrNull()
        if (valorNum != null && valorNum in 0.1..100.0) {
            _uiState.update {
                it.copy(
                    mensajeConfirmacion = "Meta mensual actualizada a $valorNum%"
                )
            }
        } else {
            _uiState.update {
                it.copy(mensajeConfirmacion = "Ingresa un porcentaje válido entre 0.1 y 100")
            }
        }
    }

    fun alternarEstadoAcceso(codigoAcceso: String, habilitado: Boolean) {
        val estadoTexto = if (habilitado) "operativo" else "cerrado por evento/mantención"
        _uiState.update { estadoActual ->
            val accesosActualizados = estadoActual.accesos.map { acceso ->
                if (acceso.codigo == codigoAcceso) {
                    acceso.copy(habilitado = habilitado)
                } else {
                    acceso
                }
            }
            estadoActual.copy(
                accesos = accesosActualizados,
                mensajeConfirmacion = "Acceso $codigoAcceso marcado como $estadoTexto"
            )
        }
    }
}