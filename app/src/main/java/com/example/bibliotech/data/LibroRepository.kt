package com.example.bibliotech.data

import com.example.bibliotech.model.Libro

class LibroRepository(
    private val libroDao: LibroDao
) {

    fun insertarLibro(libro: Libro): Long {
        return libroDao.insertarLibro(libro)
    }

    fun obtenerLibros(): List<Libro> {
        return libroDao.obtenerLibros()
    }
    fun obtenerLibroPorId(id : Int): Libro?{
        return libroDao.obtenerLibroPorId(id)
    }

    fun actualizarLibro(Libro:Libro){
        libroDao.actualizarLibro(Libro)
    }

    fun eliminarLibro(Libro:Libro){
        libroDao.eliminarLibro(Libro)
    }
}