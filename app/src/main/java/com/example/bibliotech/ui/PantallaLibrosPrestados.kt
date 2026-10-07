package com.example.bibliotech.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun PantallaLibrosPrestados(
    onRegresar: () -> Unit,
    viewModel: PrestamoViewModel = viewModel()
) {


    // ---------------------------------------------------------
    // DATOS DE LOS PRÉSTAMOS
    // ---------------------------------------------------------


    val prestamos by
    viewModel.prestamos.collectAsState()


    val librosPrestados by
    viewModel.librosPrestados.collectAsState()


    val estudiantesPrestamos by
    viewModel.estudiantesPrestamos.collectAsState()




    // ---------------------------------------------------------
    // PRÉSTAMO SELECCIONADO PARA DEVOLVER
    // ---------------------------------------------------------


    var prestamoSeleccionado by
    remember {
        mutableStateOf<com.example.bibliotech.model.Prestamo?>(null)
    }




    // ---------------------------------------------------------
    // CARGAR DATOS
    // ---------------------------------------------------------


    LaunchedEffect(Unit) {
        viewModel.cargarDatos()
    }




    // ---------------------------------------------------------
    // PANTALLA
    // ---------------------------------------------------------


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Libros prestados")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onRegresar
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->


        if (prestamos.isEmpty()) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    text = "No hay préstamos activos."
                )
            }


        } else {


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {


                items(
                    items = prestamos,
                    key = { it.id }
                ) { prestamo ->


                    val libro =
                        librosPrestados[
                            prestamo.idLibro
                        ]


                    val estudiante =
                        estudiantesPrestamos[
                            prestamo.idEstudiante
                        ]




                    // -------------------------------------------------
                    // TARJETA DEL PRÉSTAMO
                    // -------------------------------------------------


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {


                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {


                            Text(
                                text =
                                    "Libro: " +
                                            (
                                                    libro?.titulo
                                                        ?: "Cargando..."
                                                    )
                            )


                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )


                            Text(
                                text =
                                    "Autor: " +
                                            (
                                                    libro?.autor
                                                        ?: "No disponible"
                                                    )
                            )


                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )


                            Text(
                                text =
                                    "Estudiante: " +
                                            if (estudiante != null) {
                                                "${estudiante.nombres} " +
                                                        estudiante.apellidos
                                            } else {
                                                "Cargando..."
                                            }
                            )


                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )


                            Text(
                                text =
                                    "Carnet: " +
                                            (
                                                    estudiante?.carnet
                                                        ?: "No disponible"
                                                    )
                            )


                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )


                            Text(
                                text =
                                    "Fecha del préstamo: " +
                                            prestamo.fechaPrestamo
                            )


                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )




                            // -------------------------------------------------
                            // BOTÓN DEVOLVER
                            // -------------------------------------------------


                            Button(
                                onClick = {


                                    // Guardamos el préstamo
                                    // que el usuario desea devolver.
                                    prestamoSeleccionado =
                                        prestamo
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {


                                Text(
                                    text =
                                        "Marcar como devuelto"
                                )
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
        }
    }




    // ---------------------------------------------------------
    // DIÁLOGO DE CONFIRMACIÓN
    // ---------------------------------------------------------


    if (prestamoSeleccionado != null) {


        val prestamo =
            prestamoSeleccionado!!


        val libro =
            librosPrestados[
                prestamo.idLibro
            ]


        val estudiante =
            estudiantesPrestamos[
                prestamo.idEstudiante
            ]


        AlertDialog(


            onDismissRequest = {


                prestamoSeleccionado =
                    null
            },


            title = {


                Text(
                    text =
                        "Confirmar devolución"
                )
            },


            text = {


                Column {


                    Text(
                        text =
                            "¿Deseas marcar este préstamo " +
                                    "como devuelto?"
                    )


                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    Text(
                        text =
                            "Libro: " +
                                    (
                                            libro?.titulo
                                                ?: "No disponible"
                                            )
                    )


                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )


                    Text(
                        text =
                            "Estudiante: " +
                                    if (estudiante != null) {
                                        "${estudiante.nombres} " +
                                                estudiante.apellidos
                                    } else {
                                        "No disponible"
                                    }
                    )
                }
            },


            confirmButton = {


                Button(
                    onClick = {


                        // Procesamos la devolución.
                        viewModel.devolverPrestamo(
                            prestamo
                        )


                        // Cerramos el diálogo.
                        prestamoSeleccionado =
                            null
                    }
                ) {


                    Text(
                        text =
                            "Confirmar devolución"
                    )
                }
            },


            dismissButton = {


                TextButton(
                    onClick = {


                        // Cancelamos la devolución.
                        prestamoSeleccionado =
                            null
                    }
                ) {


                    Text(
                        text =
                            "Cancelar"
                    )
                }
            }
        )
    }
}
