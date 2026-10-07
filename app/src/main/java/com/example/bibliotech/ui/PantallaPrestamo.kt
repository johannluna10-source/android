package com.example.bibliotech.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliotech.viewmodel.PrestamoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrestamo(
    onRegresar: () -> Unit,
    onPrestamoGuardado: () -> Unit,
    viewModel: PrestamoViewModel = viewModel()
) {
    val estudiantes by viewModel.estudiantesActivos.collectAsState()
    val libros by viewModel.librosDisponibles.collectAsState()
    val prestamoGuardado by viewModel.prestamoGuardado.collectAsState()

    var estudianteMenuAbierto by remember { mutableStateOf(false) }
    var libroMenuAbierto by remember { mutableStateOf(false) }
    var estudianteSeleccionadoId by remember { mutableStateOf<Int?>(null) }
    var libroSeleccionadoId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.cargarDatos()
    }

    LaunchedEffect(prestamoGuardado) {
        if (prestamoGuardado) {
            onPrestamoGuardado()
            viewModel.reiniciarEstadoGuardado()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Registrar Préstamo")
                },
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Estudiante:")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { estudianteMenuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                val estudianteSeleccionado = estudiantes.find { it.id == estudianteSeleccionadoId }
                Text(
                    text = if (estudianteSeleccionado != null) {
                        "${estudianteSeleccionado.nombres} ${estudianteSeleccionado.apellidos} (${estudianteSeleccionado.carnet})"
                    } else {
                        "Seleccionar estudiante"
                    }
                )
            }
            DropdownMenu(
                expanded = estudianteMenuAbierto,
                onDismissRequest = { estudianteMenuAbierto = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                estudiantes.forEach { estudiante ->
                    DropdownMenuItem(
                        text = {
                            Text(text = "${estudiante.nombres} ${estudiante.apellidos} (${estudiante.carnet})")
                        },
                        onClick = {
                            estudianteSeleccionadoId = estudiante.id
                            estudianteMenuAbierto = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Libro:")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { libroMenuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                val libroSeleccionado = libros.find { it.id == libroSeleccionadoId }
                Text(
                    text = if (libroSeleccionado != null) {
                        "${libroSeleccionado.titulo} - ${libroSeleccionado.autor}"
                    } else {
                        "Seleccionar libro"
                    }
                )
            }
            DropdownMenu(
                expanded = libroMenuAbierto,
                onDismissRequest = { libroMenuAbierto = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                libros.forEach { libro ->
                    DropdownMenuItem(
                        text = {
                            Text(text = "${libro.titulo} - ${libro.autor}")
                        },
                        onClick = {
                            libroSeleccionadoId = libro.id
                            libroMenuAbierto = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val idEstudiante = estudianteSeleccionadoId
                    val idLibro = libroSeleccionadoId
                    if (idEstudiante != null && idLibro != null) {
                        viewModel.registrarPrestamo(
                            idLibro = idLibro,
                            idEstudiante = idEstudiante
                        )
                    }
                },
                enabled = estudianteSeleccionadoId != null && libroSeleccionadoId != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Registrar Préstamo")
            }
        }
    }
}
