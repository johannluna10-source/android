package com.example.bibliotech.util


import android.content.Context


class SesionManager(
    context: Context
) {


    private val preferencias =
        context.getSharedPreferences(
            "sesion_bibliotech",
            Context.MODE_PRIVATE
        )


    // Guarda el nombre de usuario que inició sesión.
    fun guardarSesion(usuario: String) {


        preferencias.edit()
            .putString("usuario", usuario)
            .apply()
    }


    // Comprueba si existe una sesión guardada.
    fun sesionActiva(): Boolean {


        return preferencias.contains("usuario")
    }


    // Obtiene el usuario que inició sesión.
    fun obtenerUsuario(): String? {


        return preferencias.getString(
            "usuario",
            null
        )
    }


    // Elimina la sesión guardada.
    fun cerrarSesion() {


        preferencias.edit()
            .clear()
            .apply()
    }
}
