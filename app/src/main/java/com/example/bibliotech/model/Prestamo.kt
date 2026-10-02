package com.example.bibliotech.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Prestamos",
    foreignKeys = [
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["id"],
            childColumns = ["idLibro"],
        ),
        ForeignKey(
            entity = Estudiante::class,
            parentColumns = ["id"],
            childColumns = ["idEstudiante"],
        )
    ]
)
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //Libro Prestado
    val idLibro: Int,
    //Estudiante que lo presta
    val idEstudiante: Int,
    //Fecha en que se presta
    val fechaPrestamo: String,
    //Fecha en que se debe devolver
    val fechaDevolucion: String? = null,
    //Estado del prestamo
    val devuelto: Boolean = false
)