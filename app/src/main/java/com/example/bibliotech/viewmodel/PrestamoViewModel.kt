package com.example.bibliotech.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.model.Estudiante
import com.example.bibliotech.model.Libro
import com.example.bibliotech.model.Prestamo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrestamoViewModel(aplication: Application) : AndroidViewModel(aplication) {

    private val prestamoRepository =
        (aplication as BibliotecaApplication).prestamoRepository

    private val libroRepository =
        (aplication as BibliotecaApplication).libroRepository

    private val estudianteRepository =
        (aplication as BibliotecaApplication).estudianteRepository

    // Estados de UI
    private val _librosDisponibles = MutableStateFlow<List<Libro>>(emptyList())
    val librosDisponibles: StateFlow<List<Libro>> = _librosDisponibles

    private val _estudiantesActivos = MutableStateFlow<List<Estudiante>>(emptyList())
    val estudiantesActivos: StateFlow<List<Estudiante>> = _estudiantesActivos

    private val _prestamosActivos = MutableStateFlow<List<Prestamo>>(emptyList())
    val prestamosActivos: StateFlow<List<Prestamo>> = _prestamosActivos

    private val _prestamoGuardado = MutableStateFlow<Boolean>(false)
    val prestamoGuardado: StateFlow<Boolean> = _prestamoGuardado

    fun cargarDatos() {
        viewModelScope.launch(Dispatchers.IO) {
            val libros = libroRepository.obtenerLibros()
            _librosDisponibles.value = libros.filter { it.disponible }

            val estudiantes = estudianteRepository.obtenerEstudiantes()
            _estudiantesActivos.value = estudiantes.filter { it.activo }

            _prestamosActivos.value = prestamoRepository.obtenerPrestamosActivos()
        }
    }

    fun registrarPrestamo(libroId: Int, estudianteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val libro = libroRepository.obtenerLibroPorId(libroId)
            if (libro == null || !libro.disponible) {
                return@launch
            }

            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            val nuevoPrestamo = Prestamo(
                idLibro = libroId,
                idEstudiante = estudianteId,
                fechaPrestamo = fechaActual
            )


            prestamoRepository.insertar(nuevoPrestamo)

            val libroActualizado = libro.copy(disponible = false)
            libroRepository.actualizarLibro(libroActualizado)

            val librosActualizados = libroRepository.obtenerLibros()
            _librosDisponibles.value = librosActualizados.filter { it.disponible }
            _prestamosActivos.value = prestamoRepository.obtenerPrestamosActivos()
            _prestamoGuardado.value = true
        }
    }

    fun resetearEstadoGuardado() {
        _prestamoGuardado.value = false
    }
}