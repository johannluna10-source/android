package com.example.bibliotech.viewmodel

import android.app.Application
import android.graphics.DiscretePathEffect
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.data.LibrosPrueba
import com.example.bibliotech.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PrestamoViewModel(application: Application): AndroidViewModel(application) {
    // --- TRAEMOS TODOS LOS RESPOSITORIOS
    private val prestamoRepository =
        (application as BibliotecaApplication).prestamoRepository

    private val libroRepository =
        (application as BibliotecaApplication).libroRepository
    private val estudianteRepository =
        (application as BibliotecaApplication).estudianteRepository

    // --- LIBROS DISPONIBLES
    private val _librosDisponibles =
        MutableStateFlow<List<Libro>>(emptyList())

    val librosDisponibles = _librosDisponibles

    // --- ESTUDIANTES ACTIVOS
    private val _estudiantesActivos =
        MutableStateFlow<List<Estudiante>>(emptyList())

    val estudiantesActivos = _estudiantesActivos


    // --- PRESTAMOS ACTIVOS
    private val _prestamosActivos =
        MutableStateFlow<List<Prestamo>>(emptyList())

    val prestamosActivos = _prestamosActivos


    // --- SABER SI EL PRESTAMO YA FUE GUARDADO
    private val _prestamoGuardado =
        MutableStateFlow<Boolean>(false)

    val prestamoGuardado = _prestamoGuardado

    // --- TRAER LOS DATOS AL MOMENTO DE HACER EL REGISTRO
    fun cargarDatos(){
        viewModelScope.launch(Dispatchers.IO){
            //obtenemos todos los libros
            val libros = libroRepository.obtenerLibros()

            //dejamos unicamente los libros disponibles
            _librosDisponibles.value = libros.filter { it.disponible }

            //obtenemos todos los estudiantes
            val estudiantes = estudianteRepository.obtenerEstudiantes()

            //dejamos unicamente los estudiantes activos
            _estudiantesActivos.value = estudiantes.filter { it.activo }

            //obtenemos todos los prestamos activos
            _prestamosActivos.value = prestamoRepository.obtenerPrestamosActivos()
        }
    }
    // --- GUARDAR EL PRESTAMO
    fun registrarPrestamo(libroId: Int, estudianteId: Int){
        viewModelScope.launch(Dispatchers.IO){
            //Buscamos el libro que se ha seleccionado
            val libro = libroRepository.obtenerLibroPorId(libroId)
            //verificamos que ese libro exista y que si este disponible
            if(libro == null || !libro.disponible){
                return@launch
            }

            //Obtenemos la fecha actual en el instante de guardar el prestamo
            val fechaActual = SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault()).format(Date())

            //Creamos el prestamo
            val nuevoPrestamo = Prestamo(
                idLibro = libroId,
                idEstudiante = estudianteId,
                fechaPrestamo = fechaActual,
                fechaDevolucion = null,
                devuelto = false
            )
            //Guardar en la base de datos
            prestamoRepository.insertarPrestamo(nuevoPrestamo)
            //Poner el libro en disponible como falso porque se acaba de prestar
            val libroActualizado = libro.copy(disponible = false)
            libroRepository.actualizarLibro(libroActualizado)

            //Actualizar los datos de las listaas
            val librosActualizados = libroRepository.obtenerLibros()
            _librosDisponibles.value = librosActualizados.filter { it.disponible }
            _prestamosActivos.value = prestamoRepository.obtenerPrestamosActivos()
            _prestamoGuardado.value = true
        }
    }
    //Reiniciar el estado del prestamo
    fun reiniciarEstadoGuardado(){
        _prestamoGuardado.value = false
    }
}