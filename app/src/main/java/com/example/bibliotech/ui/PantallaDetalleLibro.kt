package com.example.bibliotech.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bibliotech.R
import com.example.bibliotech.model.Libro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleLibro(
    libro: Libro,
    onRegresar: () -> Unit,
    onEditar: (Int) -> Unit,
    onEliminar: (Libro) -> Unit,
    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val mensaje = backStackEntry?.savedStateHandle?.get<String>("mensaje")

    LaunchedEffect(mensaje) {
        if (mensaje != null) {
            snackbarHostState.showSnackbar(mensaje)
            backStackEntry?.savedStateHandle?.remove<String>("mensaje")
        }
    }

    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Black,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle libro",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(paddingValues)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Gato negro en marco café",
                modifier = Modifier.size(60.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = libro.titulo,
                fontSize = 28.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Autor: ${libro.autor}",
                fontSize = 18.sp,
                color = Color.White
            )

            Text(
                text = "Categoría: ${libro.categoria}",
                fontSize = 18.sp,
                color = Color.White
            )

            Text(
                text = "Año Publicación: ${libro.anio}",
                fontSize = 18.sp,
                color = Color.White
            )

            Text(
                text = "Descripción: ${libro.descripcion}",
                fontSize = 18.sp,
                color = Color.White
            )

            Text(
                text = "Disponibilidad: ${if (libro.disponible) "Disponible" else "Prestado"}",
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        onEditar(libro.id)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = {
                        mostrarDialogo = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onRegresar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Regresar")
            }

            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = {
                        Text("Confirmación")
                    },
                    text = {
                        Text("¿Estás seguro de eliminar \"${libro.titulo}\"?")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                mostrarDialogo = false
                                onEliminar(libro)
                            }
                        ) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                mostrarDialogo = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
