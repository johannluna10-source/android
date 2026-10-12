package com.example.bibliotech.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bibliotech.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun insertarUsuario(usuario: Usuario): Long

    @Query("SELECT * FROM Usuarios")
    fun obtenerUsuarios(): List<Usuario>

    @Query("SELECT * FROM Usuarios WHERE usuario = :nombreUsuario LIMIT 1")
    fun obtenerUsuarioPorNombre(
        nombreUsuario: String
    ): Usuario?

    @Query(
        "SELECT * FROM Usuarios " +
                "WHERE usuario = :nombreUsuario " +
                "AND contrasena = :contrasena " +
                "LIMIT 1"
    )
    fun validarUsuario(
        nombreUsuario: String,
        contrasena: String
    ): Usuario?
}