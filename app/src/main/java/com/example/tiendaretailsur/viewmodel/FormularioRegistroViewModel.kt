package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.FormularioRegistroUiState
import com.example.tiendaretailsur.model.RolUsuario
import com.example.tiendaretailsur.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularioRegistroViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormularioRegistroUiState())
    val uiState: StateFlow<FormularioRegistroUiState> = _uiState.asStateFlow()

    fun onRutCambia(nuevoRut: String) {
        val rutFormateado = autoCompletadoRut(nuevoRut)
        _uiState.update {
            it.copy(rut = rutFormateado, errorFormatoRut = null, registroExitoso = false)
        }
    }

    fun onNombreCambia(valor: String) {
        _uiState.update {
            it.copy(nombre = valor, errorNombre = null, registroExitoso = false)
        }
    }

    fun onCorreoCambia(valor: String) {
        _uiState.update {
            it.copy(correo = valor, errorFormatoCorreo = null, registroExitoso = false)
        }
    }

    fun onTelefonoCambia(valor: String) {
        val soloNumeros = valor.filter { it.isDigit() }.take(9)
        _uiState.update {
            it.copy(telefono = soloNumeros, errorTelefono = null, registroExitoso = false)
        }
    }

    fun onContrasenaCambia(valor: String) {
        _uiState.update {
            it.copy(contrasena = valor, errorContrasena = null, registroExitoso = false)
        }
    }

    fun onAlternarContrasenaVisible() {
        _uiState.update { it.copy(contrasenaVisible = !it.contrasenaVisible) }
    }

    fun onSeleccionRol(rolSeleccionado : RolUsuario){
        _uiState.update { it.copy(rolSeleccionado = rolSeleccionado) }
    }

    fun onTerminosCambia(valor: Boolean) {
        _uiState.update { it.copy(aceptarTerminos = valor, errorTerminos = null) }
    }

    fun onRegistrar() : Usuario? {
        val estado = _uiState.value

        val errorRut = if (!validarFormatoRut(estado.rut)) "El RUT no tiene un formato válido" else null
        val errorNombre = if (estado.nombre.isBlank()) "El nombre es obligatorio" else null
        val errorCorreo = if (!estado.correo.contains("@") || !estado.correo.contains(".")) {
            "Ingrese un correo válido"
        } else null
        val errorTelefono = if (estado.telefono.length < 9) {
            "El número de teléfono debe tener 9 dígitos"
        } else null
        val errorContrasena = validarContrasena(estado.contrasena)
        val errorTerminos = if (!estado.aceptarTerminos) "Debes aceptar los términos" else null

        val siHayErrores = listOf(
            errorRut,
            errorNombre,
            errorCorreo,
            errorTelefono,
            errorContrasena,
            errorTerminos
        ).any { it != null }

        _uiState.update {
            it.copy(
                errorFormatoRut = errorRut,
                errorNombre = errorNombre,
                errorFormatoCorreo = errorCorreo,
                errorTelefono = errorTelefono,
                errorContrasena = errorContrasena,
                errorTerminos = errorTerminos,
                registroExitoso = !siHayErrores
            )
        }
        return if (siHayErrores) null else Usuario(estado.rut, estado.nombre, estado.correo)
    }

    fun validarFormatoRut(rut: String): Boolean {
        val limpiarRut = rut.replace(".", "")
            .replace("-", "")
            .trim()
            .uppercase()

        if (limpiarRut.length < 8) return false

        val cuerpoRut = limpiarRut.substring(0, limpiarRut.length - 1)
        val dvRut = limpiarRut.last()
        cuerpoRut.toIntOrNull() ?: return false

        var suma = 0
        var multiplicador = 2

        for (i in cuerpoRut.length - 1 downTo 0) {
            suma += (cuerpoRut[i].toString().toInt()) * multiplicador
            multiplicador = if (multiplicador < 7) multiplicador + 1 else 2
        }
        val resto = suma % 11
        val dvRutCalculado = when (val resultado = 11 - resto) {
            11 -> '0'
            10 -> 'K'
            else -> resultado.toString()[0]
        }
        return dvRut == dvRutCalculado
    }

    fun autoCompletadoRut(rut: String): String {
        val limpiar = rut.replace("[^0-9kK]".toRegex(), "").uppercase().take(9)
        if (limpiar.isEmpty()) return ""

        val dv = limpiar.last()
        val cuerpo = limpiar.dropLast(1)

        if (cuerpo.isEmpty()) return dv.toString()
        val cuerpoFormateado = cuerpo.reversed().chunked(3).joinToString(".").reversed()
        return "$cuerpoFormateado-$dv"
    }

    private fun validarContrasena(contrasena: String): String? {
        if (contrasena.length < 8) return "La contraseña debe tener al menos 8 caracteres"
        if (!contrasena.any { it.isUpperCase() }) return "La contraseña debe tener al menos una mayúscula"
        if (!contrasena.any { it.isLowerCase() }) return "La contraseña debe tener al menos una minúscula"
        if (!contrasena.any { it.isDigit() }) return "La contraseña debe tener al menos un número"
        return null
    }
}