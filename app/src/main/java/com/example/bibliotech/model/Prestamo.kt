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
    val id: Int= 0,
    val idLibro: Int,
    val idEstudiante: Int,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val devuelto: Boolean=false
)