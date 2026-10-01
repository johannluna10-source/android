package com.example.bibliotech.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bibliotech.model.Prestamo

@Dao
interface PrestamoDao {

    @Insert
    suspend fun insertar(prestamo: Prestamo)

    @Query("SELECT * FROM Prestamos WHERE devuelto = 0")
    suspend fun obtenerPrestamosActivos(): List<Prestamo>

    @Query("SELECT * FROM Prestamos WHERE id = :id")
    suspend fun obtenerPrestamoPorId(id: Int): Prestamo?

    @Update
    fun actualizarPrestamo(prestamo: Prestamo)

}