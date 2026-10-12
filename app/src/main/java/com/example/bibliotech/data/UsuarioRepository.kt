package com.example.bibliotech.data

import com.example.bibliotech.model.Usuario

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    fun insertarUsuario(usuario: Usuario): Long {
        return usuarioDao.insertarUsuario(usuario)
    }

    fun obtenerUsuarios(): List<Usuario> {
        return usuarioDao.obtenerUsuarios()
    }

    fun obtenerUsuarioPorNombre(
        nombreUsuario: String
    ): Usuario? {
        return usuarioDao.obtenerUsuarioPorNombre(
            nombreUsuario
        )
    }

    fun validarUsuario(
        nombreUsuario: String,
        contrasena: String
    ): Usuario? {
        return usuarioDao.validarUsuario(
            nombreUsuario,
            contrasena
        )
    }
}