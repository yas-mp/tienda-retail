package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.AjustesAdminUiState
import com.example.tiendaretailsur.model.ConfiguracionPerfil
import com.example.tiendaretailsur.model.SeccionAjustesAdmin
import com.example.tiendaretailsur.model.Usuario
import com.example.tiendaretailsur.model.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AjustesAdminViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AjustesAdminUiState())
    val uiState: StateFlow<AjustesAdminUiState> = _uiState.asStateFlow()

    init {
        recargarDatos()
    }

    fun recargarDatos() {
        _uiState.update {
            it.copy(
                usuarios = UsuarioRepository.obtenerTodosLosUsuarios(),
                perfiles = UsuarioRepository.obtenerPerfiles(),
                accesos = UsuarioRepository.obtenerAccesos(),
                metaConversionTexto = UsuarioRepository.obtenerMetaConversion().toString()
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

    // Habilitar / Deshabilitar colaborador directamente desde la tarjeta
    fun alternarEstadoUsuario(usuario: Usuario, nuevoEstado: Boolean) {
        UsuarioRepository.cambiarEstadoUsuario(usuario.codigo, nuevoEstado)
        val estadoTexto = if (nuevoEstado) "habilitado" else "deshabilitado"
        _uiState.update {
            it.copy(
                usuarios = UsuarioRepository.obtenerTodosLosUsuarios(),
                mensajeConfirmacion = "Colaborador ${usuario.nombreCompleto} $estadoTexto"
            )
        }
    }

    // Abrir modal para editar todos los datos de un colaborador
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
        if (estado.editNuevaContrasena.isNotBlank() && estado.editNuevaContrasena.length < 8) {
            _uiState.update { it.copy(errorEdicionUsuario = "La nueva contraseña debe tener mínimo 8 caracteres") }
            return
        }

        UsuarioRepository.actualizarDatosUsuario(
            codigoOriginal = usuarioOriginal.codigo,
            nuevoNombre = estado.editNombre,
            nuevoRut = estado.editRut,
            nuevoCorreo = estado.editCorreo,
            nuevoTelefono = estado.editTelefono,
            nuevoCargo = estado.editCargo,
            nuevoPerfil = estado.editPerfil,
            activo = estado.editActivo,
            nuevaContrasenaOpcional = estado.editNuevaContrasena.takeIf { it.isNotBlank() }
        )

        _uiState.update {
            it.copy(
                usuarios = UsuarioRepository.obtenerTodosLosUsuarios(),
                usuarioEnEdicion = null,
                errorEdicionUsuario = null,
                mensajeConfirmacion = "Datos de ${estado.editNombre} actualizados correctamente"
            )
        }
    }

    // Habilitar / Deshabilitar un Perfil completo de la aplicación (P-01 a P-04)
    fun alternarEstadoPerfil(perfil: ConfiguracionPerfil, habilitado: Boolean) {
        UsuarioRepository.cambiarEstadoPerfil(perfil.codigo, habilitado)
        val textoEstado = if (habilitado) "habilitado" else "deshabilitado"
        _uiState.update {
            it.copy(
                perfiles = UsuarioRepository.obtenerPerfiles(),
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

        UsuarioRepository.actualizarPerfilConfig(
            codigoPerfil = perfil.codigo,
            nuevoNombre = estado.editPerfilNombre,
            nuevaDescripcion = estado.editPerfilDescripcion,
            habilitado = estado.editPerfilHabilitado,
            aplicarEstadoAUsuariosDelPerfil = estado.aplicarEstadoATodosLosUsuariosDelPerfil
        )

        _uiState.update {
            it.copy(
                perfiles = UsuarioRepository.obtenerPerfiles(),
                usuarios = UsuarioRepository.obtenerTodosLosUsuarios(),
                perfilEnEdicion = null,
                mensajeConfirmacion = "Configuración del perfil ${perfil.codigo} guardada"
            )
        }
    }

    // Ajustes de Tienda: Meta de Conversión y Accesos A1 a A4
    fun onMetaConversionCambia(valor: String) {
        _uiState.update { it.copy(metaConversionTexto = valor) }
    }

    fun guardarMetaConversion() {
        val valorNum = _uiState.value.metaConversionTexto.replace(",", ".").toDoubleOrNull()
        if (valorNum != null && valorNum in 0.1..100.0) {
            UsuarioRepository.actualizarMetaConversion(valorNum)
            _uiState.update {
                it.copy(
                    metaConversionTexto = UsuarioRepository.obtenerMetaConversion().toString(),
                    mensajeConfirmacion = "Meta mensual actualizada a ${UsuarioRepository.obtenerMetaConversion()}%"
                )
            }
        } else {
            _uiState.update {
                it.copy(mensajeConfirmacion = "Ingresa un porcentaje válido entre 0.1 y 100")
            }
        }
    }

    fun alternarEstadoAcceso(codigoAcceso: String, habilitado: Boolean) {
        UsuarioRepository.cambiarEstadoAcceso(codigoAcceso, habilitado)
        val estadoTexto = if (habilitado) "operativo" else "cerrado por evento/mantención"
        _uiState.update {
            it.copy(
                accesos = UsuarioRepository.obtenerAccesos(),
                mensajeConfirmacion = "Acceso $codigoAcceso marcado como $estadoTexto"
            )
        }
    }
}