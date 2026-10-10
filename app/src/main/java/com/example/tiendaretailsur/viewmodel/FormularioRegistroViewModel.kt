package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.FormularioRegistroUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularioRegistroViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(FormularioRegistroUiState())
    val uiState: StateFlow<FormularioRegistroUiState> = _uiState.asStateFlow()

    fun onRutCambia(nuevoRut : String){
        val rutFormateado = autoCompletadoRut(nuevoRut)
        _uiState.update { it.copy(rut = rutFormateado, errorFormatoRut = null, registroExitoso = false) }
    }
    fun onNombreCambia(valor : String){
        _uiState.update { it.copy(nombre = valor, errorNombre = null, registroExitoso = false) }
    }
    fun onApellidoCambia(valor : String){
        _uiState.update{ it.copy(apellido = valor, errorApellido = null, registroExitoso = false)}
    }
    fun onCorreoCambia(valor : String){
        _uiState.update{ it.copy(correo = valor, errorFormatoCorreo = null, registroExitoso = false)}
    }
    fun onTelefonoCambia(valor : String){
        _uiState.update{ it.copy(telefono = valor, errorTelefono = null, registroExitoso = false)}
    }
    fun onContrasenaCambia(valor : String){
        _uiState.update{ it.copy(contrasena = valor, errorContrasena = null, registroExitoso = false)}
    }
    fun onAlternarContrasenaVisible(){
        _uiState.update{ it.copy(contrasenaVisible = !it.contrasenaVisible)}
    }
    fun onTerminosCambia(valor : Boolean){
        _uiState.update { it.copy(aceptarTerminos = valor, errorTerminos = null) }
    }
    fun onLimpiarFormulario(){
        _uiState.value = FormularioRegistroUiState()
    }

    fun onRegistrar(){
        val estado = _uiState.value

        val errorRut = if (!validarFormatoRut(estado.rut)) "El rut no tiene el formato solicitado" else null
        val errorNombre = if(estado.nombre.isBlank()) "El nombre es obligatorio" else null
        val errorApellido = if(estado.apellido.isBlank()) "El apellido es obligatorio" else null

        val errorCorreo = if(!estado.correo.contains("@") || !estado.correo.contains("."))
            "Ingrese un correo válido" else null

        val errorTelefono = if(estado.telefono.length < 9) "El número de telefóno debe tener 9 números" else null
        val errorContrasena = validarContrasena(estado.contrasena)
        val errorTerminos = if(!estado.aceptarTerminos) "Debes aceptar los términos" else null

        val siHayErrores = listOf(errorRut, errorNombre,
            errorApellido, errorCorreo,
            errorTelefono, errorContrasena, errorTerminos).any { it != null }
        _uiState.update { it.copy(
            errorFormatoRut = errorRut,
            errorNombre = errorNombre,
            errorApellido = errorApellido,
            errorFormatoCorreo = errorCorreo,
            errorTelefono = errorTelefono,
            errorContrasena = errorContrasena,
            errorTerminos = errorTerminos,
            registroExitoso = !siHayErrores
        ) }

    }
    fun validarFormatoRut(rut : String) : Boolean{
        val limpiarRut = rut.replace(".","")
            .replace("-","")
            .trim()
            .uppercase()

        if(limpiarRut.length < 8) return false

        val cuerpoRut = limpiarRut.substring(0,limpiarRut.length - 1)
        val dvRut = limpiarRut.last()
        val cuerpoNum = cuerpoRut.toIntOrNull() ?: return false

        var suma = 0
        var multiplicador = 2

        for(i in cuerpoRut.length - 1 downTo 0){
            suma += (cuerpoRut[i].toString().toInt()) * multiplicador
            multiplicador = if(multiplicador < 7) multiplicador + 1 else 2
        }
        val resto = suma % 11
        val dvRutCalculado = when(val resultado = 11 - resto){
            11 -> '0'
            10 -> 'K'
            else -> resultado.toString()[0]
        }
        return dvRut == dvRutCalculado
    }
    fun autoCompletadoRut(rut : String) : String{
        val limpiar = rut.replace("[^0-9kK]".toRegex(), "").uppercase()
        if(limpiar.isEmpty()) return ""

        val dv = limpiar.last()
        val cuerpo = limpiar.dropLast(1)

        if (cuerpo.isEmpty()) return dv.toString()
        val cuerpoFormateado = cuerpo.reversed().chunked(3).joinToString(".").reversed()
        return "$cuerpoFormateado-$dv"
    }
    private fun validarContrasena(contrasena : String) : String? {
        if(contrasena.length < 8) return "La contrasena debe tener al menos 8 carácteres"
        if(!contrasena.any { it.isUpperCase() }) return "La contrasena debe tener al menos una mayúscula"
        if(!contrasena.any { it.isLowerCase()}) return "La contrasena debe tener al menos una minúscula"
        if(!contrasena.any { it.isDigit() }) return "La contrasena debe tener al menos un número"
        if(!contrasena.any { it.isLetterOrDigit()}) return "La contrasena debe incluir al menos un carácter"
        return null
    }

}