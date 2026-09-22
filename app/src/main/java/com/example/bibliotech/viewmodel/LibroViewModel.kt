package com.example.bibliotech.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.model.Libro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibroViewModel (application: Application): AndroidViewModel(application){
    private  val repository=
        (application as BibliotecaApplication).libroRepository
    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()
    private val _libroSeleccionado = MutableStateFlow<Libro?>(null)
    val libroSeleccionado: StateFlow<Libro?> = _libroSeleccionado.asStateFlow()

    fun cargarLibros(){
        viewModelScope.launch (Dispatchers.IO ){
            _libros.value = repository.obtenerLibros()
        }
    }

    fun insertarLibros(libro: Libro){
        viewModelScope.launch (Dispatchers.IO ){
            repository.insertarLibro(libro)
            _libros.value= repository.obtenerLibros()
        }
    }

    fun cargarLibroPorId(id: Int){
        viewModelScope.launch (Dispatchers.IO ){
_libroSeleccionado.value=repository.obtenerLibroPorId(id)
        }
    }

    fun actualizarLibro(Libro:Libro){
        viewModelScope.launch(Dispatchers.IO){
            repository.actualizarLibro(Libro)
            _libros.value=repository.obtenerLibros()
            _libroSeleccionado.value=repository.obtenerLibroPorId(Libro.id)
        }
    }

    fun eliminarLibro(Libro:Libro){
        viewModelScope.launch(Dispatchers.IO){
            repository.eliminarLibro(Libro)
            _libros.value=repository.obtenerLibros()
        }
    }
}