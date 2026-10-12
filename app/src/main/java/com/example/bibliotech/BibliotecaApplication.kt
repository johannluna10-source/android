package com.example.bibliotech

import android.app.Application
import com.example.bibliotech.data.DatabaseProvider
import com.example.bibliotech.data.BibliotecaDatabase
import com.example.bibliotech.data.EstudianteRepository
import com.example.bibliotech.data.LibroRepository
import com.example.bibliotech.data.PrestamoRepository
import com.example.bibliotech.data.UsuarioRepository

class BibliotecaApplication : Application() {

    val database: BibliotecaDatabase by lazy {
        DatabaseProvider.getDatabase(this)
    }

    val libroDao by lazy {
        database.libroDao()
    }

    val estudianteDao by lazy {
        database.estudianteDao()
    }

    val prestamoDao by lazy {
        database.prestamoDao()
    }

    val usuarioDao by lazy {
        database.usuarioDao()
    }

    val libroRepository by lazy {
        LibroRepository(libroDao)
    }

    val estudianteRepository by lazy {
        EstudianteRepository(estudianteDao)
    }

    val prestamoRepository by lazy {
        PrestamoRepository(prestamoDao)
    }

    val usuarioRepository by lazy {
        UsuarioRepository(usuarioDao)
    }
}