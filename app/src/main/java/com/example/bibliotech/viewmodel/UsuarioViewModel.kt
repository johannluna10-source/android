package com.example.bibliotech.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.model.Usuario
import com.example.bibliotech.util.SesionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

class UsuarioViewModel(application: Application) :
    AndroidViewModel(application) {

    private val usuarioRepository =
        (application as BibliotecaApplication).usuarioRepository

    private val sesionManager =
        SesionManager(application)

    private val _registroExitoso =
        MutableStateFlow(false)

    val registroExitoso: StateFlow<Boolean> =
        _registroExitoso.asStateFlow()

    private val _usuarioExiste =
        MutableStateFlow(false)

    val usuarioExiste: StateFlow<Boolean> =
        _usuarioExiste.asStateFlow()

    private val _loginExitoso =
        MutableStateFlow(false)

    val loginExitoso: StateFlow<Boolean> =
        _loginExitoso.asStateFlow()

    private val _loginIncorrecto =
        MutableStateFlow(false)

    val loginIncorrecto: StateFlow<Boolean> =
        _loginIncorrecto.asStateFlow()

    fun registrarUsuario(
        nombre: String,
        usuario: String,
        contrasena: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val usuarioExistente =
                usuarioRepository.obtenerUsuarioPorNombre(usuario)

            if (usuarioExistente != null) {
                _usuarioExiste.value = true
                _registroExitoso.value = false
                return@launch
            }

            val contrasenaHash = generarHash(contrasena)

            val nuevoUsuario = Usuario(
                nombre = nombre,
                usuario = usuario,
                contrasena = contrasenaHash
            )

            usuarioRepository.insertarUsuario(nuevoUsuario)

            _usuarioExiste.value = false
            _registroExitoso.value = true
        }
    }

    fun iniciarSesion(
        usuario: String,
        contrasena: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val contrasenaHash = generarHash(contrasena)

            val usuarioEncontrado =
                usuarioRepository.validarUsuario(
                    nombreUsuario = usuario,
                    contrasena = contrasenaHash
                )

            if (usuarioEncontrado != null) {
                sesionManager.guardarSesion(usuarioEncontrado.usuario)
                _loginExitoso.value = true
                _loginIncorrecto.value = false
            } else {
                _loginExitoso.value = false
                _loginIncorrecto.value = true
            }
        }
    }

    fun reiniciarEstado() {
        _registroExitoso.value = false
        _usuarioExiste.value = false
        _loginExitoso.value = false
        _loginIncorrecto.value = false
    }

    fun comprobarSesion(): Boolean {
        return sesionManager.sesionActiva()
    }

    fun obtenerUsuarioActual(): String? {
        return sesionManager.obtenerUsuario()
    }

    fun cerrarSesion() {
        sesionManager.cerrarSesion()
    }

    private fun generarHash(contrasena: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = contrasena.toByteArray()
        val hashBytes = digest.digest(bytes)
        return hashBytes.joinToString("") {
            "%02x".format(it)
        }
    }
}
