package com.example.tiendaretailsur.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiendaretailsur.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {
    private val _usuarios = MutableStateFlow(
        listOf(Usuario("21.123.111-8","Jasmin Valencia","jasmin@gmail.com"))
    )
    val usuarios : StateFlow<List<Usuario>> = _usuarios.asStateFlow()

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual : StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    fun registrarUsuario(usuario : Usuario){
        _usuarios.update { lista -> lista + usuario }
        _usuarioActual.value = usuario
    }

    fun actualizarUsuario(indice : Int, editado : Usuario){
        val anterior = _usuarios.value[indice]
        _usuarios.update { lista -> lista.mapIndexed { i, u -> if(i == indice) editado else u } }
        if (_usuarioActual.value == anterior) _usuarioActual.value = editado
    }

    fun cerrarSesion(){
        _usuarioActual.value = null
    }
}