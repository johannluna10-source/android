
package com.example.bibliotech.viewmodel
/*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.model.Prestamo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PrestamoViewModel(application: Application) : AndroidViewModel(application) {


   // Obtenemos el Repository de préstamos desde nuestra Application.
   private val repository =
       (application as BibliotecaApplication).prestamoRepository


   // Lista de préstamos activos.
   private val _prestamos =
       MutableStateFlow<List<Prestamo>>(emptyList())


   val prestamos: StateFlow<List<Prestamo>> =
       _prestamos.asStateFlow()




   // ---------------------------------------------------------
   // CARGAR PRÉSTAMOS ACTIVOS
   // ---------------------------------------------------------


   fun cargarPrestamosActivos() {


       // Las operaciones con Room se ejecutan en segundo plano.
       viewModelScope.launch(Dispatchers.IO) {


           _prestamos.value =
               repository.obtenerPrestamosActivos()
       }
   }




   // ---------------------------------------------------------
   // REGISTRAR PRÉSTAMO
   // ---------------------------------------------------------


   fun insertarPrestamo(prestamo: Prestamo) {


       viewModelScope.launch(Dispatchers.IO) {


           // Guardamos el préstamo en la base de datos.
           repository.insertarPrestamo(prestamo)


           // Actualizamos la lista después de guardar.
           _prestamos.value =
               repository.obtenerPrestamosActivos()
       }
   }




   // ---------------------------------------------------------
   // ACTUALIZAR PRÉSTAMO
   // ---------------------------------------------------------


   fun actualizarPrestamo(prestamo: Prestamo) {


       viewModelScope.launch(Dispatchers.IO) {


           // Actualizamos el préstamo.
           repository.actualizarPrestamo(prestamo)


           // Volvemos a cargar la lista.
           _prestamos.value =
               repository.obtenerPrestamosActivos()
       }
   }
}*/


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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PrestamoViewModel(application: Application) : AndroidViewModel(application) {


    // ---------------------------------------------------------
    // REPOSITORIES
    // ---------------------------------------------------------


    private val prestamoRepository =
        (application as BibliotecaApplication).prestamoRepository


    private val libroRepository =
        (application as BibliotecaApplication).libroRepository


    private val estudianteRepository =
        (application as BibliotecaApplication).estudianteRepository




    // ---------------------------------------------------------
    // LIBROS DISPONIBLES
    // ---------------------------------------------------------


    private val _librosDisponibles =
        MutableStateFlow<List<Libro>>(emptyList())


    val librosDisponibles: StateFlow<List<Libro>> =
        _librosDisponibles.asStateFlow()




    // ---------------------------------------------------------
    // ESTUDIANTES ACTIVOS
    // ---------------------------------------------------------


    private val _estudiantesActivos =
        MutableStateFlow<List<Estudiante>>(emptyList())


    val estudiantesActivos: StateFlow<List<Estudiante>> =
        _estudiantesActivos.asStateFlow()




    // ---------------------------------------------------------
    // PRÉSTAMOS ACTIVOS
    // ---------------------------------------------------------


    private val _prestamos =
        MutableStateFlow<List<Prestamo>>(emptyList())


    val prestamos: StateFlow<List<Prestamo>> =
        _prestamos.asStateFlow()




    // ---------------------------------------------------------
    // INDICA SI EL PRÉSTAMO SE GUARDÓ
    // ---------------------------------------------------------


    private val _prestamoGuardado =
        MutableStateFlow(false)


    val prestamoGuardado: StateFlow<Boolean> =
        _prestamoGuardado.asStateFlow()




    // ---------------------------------------------------------
    // CARGAR DATOS PARA EL REGISTRO
    // ---------------------------------------------------------


    /*fun cargarDatos() {


        viewModelScope.launch(Dispatchers.IO) {


            // Obtenemos todos los libros.
            val libros = libroRepository.obtenerLibros()


            // Dejamos únicamente los libros disponibles.
            _librosDisponibles.value =
                libros.filter { it.disponible }


            // Obtenemos todos los estudiantes.
            val estudiantes =
                estudianteRepository.obtenerEstudiantes()


            // Dejamos únicamente los estudiantes activos.
            _estudiantesActivos.value =
                estudiantes.filter { it.activo }


            // También cargamos los préstamos activos.
            _prestamos.value =
                prestamoRepository.obtenerPrestamosActivos()


        }
    }*/


    // ---------------------------------------------------------
    // REGISTRAR PRÉSTAMO
    // ---------------------------------------------------------


    fun registrarPrestamo(
        idLibro: Int,
        idEstudiante: Int
    ) {


        viewModelScope.launch(Dispatchers.IO) {


            // Buscamos el libro seleccionado.
            val libro =
                libroRepository.obtenerLibroPorId(idLibro)


            // Verificamos que el libro exista y esté disponible.
            if (libro == null || !libro.disponible) {
                return@launch
            }


            // Obtenemos la fecha actual.
            val fechaActual =
                SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date())


            // Creamos el nuevo préstamo.
            val nuevoPrestamo = Prestamo(
                idLibro = idLibro,
                idEstudiante = idEstudiante,
                fechaPrestamo = fechaActual,
                fechaDevolucion = null,
                devuelto = false
            )


            // Guardamos el préstamo.
            prestamoRepository.insertarPrestamo(
                nuevoPrestamo
            )


            // El libro deja de estar disponible.
            val libroActualizado =
                libro.copy(
                    disponible = false
                )


            libroRepository.actualizarLibro(
                libroActualizado
            )


            // Actualizamos las listas.
            val librosActualizados =
                libroRepository.obtenerLibros()


            _librosDisponibles.value =
                librosActualizados.filter { it.disponible }


            _prestamos.value =
                prestamoRepository.obtenerPrestamosActivos()


            // Indicamos que el registro terminó correctamente.
            _prestamoGuardado.value = true
        }
    }




    // ---------------------------------------------------------
    // REINICIAR ESTADO DE GUARDADO
    // ---------------------------------------------------------


    fun reiniciarEstadoGuardado() {
        _prestamoGuardado.value = false
    }










    // NUEVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO


    // ------
    // ---------------------------------------------------------
    // LIBROS RELACIONADOS CON LOS PRÉSTAMOS
    // ---------------------------------------------------------


    private val _librosPrestados =
        MutableStateFlow<Map<Int, Libro>>(emptyMap())


    val librosPrestados: StateFlow<Map<Int, Libro>> =
        _librosPrestados.asStateFlow()




    // ---------------------------------------------------------
    // ESTUDIANTES RELACIONADOS CON LOS PRÉSTAMOS
    // ---------------------------------------------------------


    private val _estudiantesPrestamos =
        MutableStateFlow<Map<Int, Estudiante>>(emptyMap())


    val estudiantesPrestamos: StateFlow<Map<Int, Estudiante>> =
        _estudiantesPrestamos.asStateFlow()


    // ---------------------------------------------------------
    // CARGAR DATOS
    // ---------------------------------------------------------


    fun cargarDatos() {


        viewModelScope.launch(Dispatchers.IO) {


            // Obtenemos todos los libros.
            val libros =
                libroRepository.obtenerLibros()


            // Dejamos únicamente los disponibles.
            _librosDisponibles.value =
                libros.filter {
                    it.disponible
                }


            // Obtenemos todos los estudiantes.
            val estudiantes =
                estudianteRepository.obtenerEstudiantes()


            // Dejamos únicamente los activos.
            _estudiantesActivos.value =
                estudiantes.filter {
                    it.activo
                }


            // Obtenemos los préstamos activos.
            val prestamos =
                prestamoRepository.obtenerPrestamosActivos()


            _prestamos.value = prestamos


            // -------------------------------------------------
            // PREPARAMOS LOS LIBROS DE LOS PRÉSTAMOS
            // -------------------------------------------------


            val mapaLibros =
                mutableMapOf<Int, Libro>()


            prestamos.forEach { prestamo ->


                val libro =
                    libroRepository.obtenerLibroPorId(
                        prestamo.idLibro
                    )


                if (libro != null) {
                    mapaLibros[prestamo.idLibro] =
                        libro
                }
            }


            _librosPrestados.value =
                mapaLibros




            // -------------------------------------------------
            // PREPARAMOS LOS ESTUDIANTES DE LOS PRÉSTAMOS
            // -------------------------------------------------


            val mapaEstudiantes =
                mutableMapOf<Int, Estudiante>()


            prestamos.forEach { prestamo ->


                val estudiante =
                    estudianteRepository.obtenerEstudiantePorId(
                        prestamo.idEstudiante
                    )


                if (estudiante != null) {
                    mapaEstudiantes[prestamo.idEstudiante] =
                        estudiante
                }
            }


            _estudiantesPrestamos.value =
                mapaEstudiantes
        }
    }


    // ---------------------------------------------------------
    // DEVOLVER LIBRO
    // ---------------------------------------------------------


    fun devolverPrestamo(
        prestamo: Prestamo
    ) {


        viewModelScope.launch(Dispatchers.IO) {


            // Fecha actual de la devolución.
            val fechaActual =
                SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date())


            // Marcamos el préstamo como devuelto.
            val prestamoActualizado =
                prestamo.copy(
                    devuelto = true,
                    fechaDevolucion = fechaActual
                )


            prestamoRepository.actualizarPrestamo(
                prestamoActualizado
            )


            // Buscamos el libro asociado.
            val libro =
                libroRepository.obtenerLibroPorId(
                    prestamo.idLibro
                )


            // Volvemos a poner el libro como disponible.
            if (libro != null) {


                val libroActualizado =
                    libro.copy(
                        disponible = true
                    )


                libroRepository.actualizarLibro(
                    libroActualizado
                )
            }


            // Actualizamos la lista de préstamos activos.
            _prestamos.value =
                prestamoRepository.obtenerPrestamosActivos()


            // Actualizamos también los libros disponibles.
            val librosActualizados =
                libroRepository.obtenerLibros()


            _librosDisponibles.value =
                librosActualizados.filter {
                    it.disponible
                }
        }
    }


}
