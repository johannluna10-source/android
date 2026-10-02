package com.example.bibliotech

import android.app.Application
import com.example.bibliotech.data.BibliotecaDatabase
import com.example.bibliotech.data.DatabaseProvider
import com.example.bibliotech.data.EstudianteRepository
import com.example.bibliotech.data.LibroRepository
import com.example.bibliotech.data.LibrosPrueba
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.getValue
import com.example.bibliotech.data.PrestamoRepository

class BibliotecaApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: BibliotecaDatabase by lazy {
        DatabaseProvider.getDatabase(this)
    }

    // Configuración para Libros
    val libroDao
        get() = database.libroDao()

    val libroRepository: LibroRepository by lazy {
        LibroRepository(libroDao)
    }

    // Configuración para Préstamos
    val prestamoDao
        get() = database.prestamoDao()

    // CORREGIDO: Se cambió 'prestampRepository' por 'prestamoRepository'
    val prestamoRepository: PrestamoRepository by lazy {
        PrestamoRepository(prestamoDao)
    }

    // Configuración para Estudiantes
    val estudianteDao
        get() = database.estudianteDao()

    val estudianteRepository: EstudianteRepository by lazy {
        EstudianteRepository(estudianteDao)
    }

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            if (libroRepository.obtenerLibros().isEmpty()) {
                LibrosPrueba.forEach { libro ->
                    libroRepository.insertarLibro(libro)
                }
            }

            val libros = libroRepository.obtenerLibros()
            println("LIBROS EN ROOM: ${libros.size}")
            libros.forEachIndexed { indice, libro ->
                // CORREGIDO: Sintaxis del salto de línea dentro del String
                println("Libro ${indice + 1}: ${libro.titulo}")
            }
        }
    }
}