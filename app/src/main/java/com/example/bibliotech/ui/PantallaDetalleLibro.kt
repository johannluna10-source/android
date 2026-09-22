package com.example.bibliotech.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.model.Libro
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.bibliotech.R
import android.R.attr.title
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun PantallaDetalleLibro(Libro:Libro,
                         onRegresar:()-> Unit,
                         onEditar:(Int) -> Unit,
                         onEliminar:(Libro) -> Unit,
                         navController: NavController,
){
    val snackbarHostState= remember { SnackbarHostState() }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val mensaje=
        backStackEntry
            ?.savedStateHandle
            ?.get<String>("mensaje")

    LaunchedEffect(mensaje) {
        if (mensaje != null){
            snackbarHostState.showSnackbar(mensaje)
            backStackEntry
                ?.savedStateHandle
                ?.remove<String>("mensaje")
        }
    }

var mostrarDialogo by remember { mutableStateOf(false) }
    @OptIn(ExperimentalMaterial3Api::class)

    Scaffold(containerColor = Color.Black,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
            title={
                Text("Detalle libro",
                    color=Color.White
                    )
       },
colors = TopAppBarDefaults.topAppBarColors(
    containerColor = Color.Black
)
            )

        }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(20.dp).padding(paddingValues)) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Gato negro en marco café",
                modifier = Modifier.size(60.dp),
                //hace que mantenga sus colores reales
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = Libro.titulo,
                fontSize = 28.sp,
                color = Color.White

            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Autor ${Libro.autor}",
                fontSize = 18.sp,
                color = Color.White

            )
            Text(
                text = "Categoria ${Libro.categoria}",
                fontSize = 18.sp,
                color = Color.White

            )
            Text(
                text = "Año Publicacion ${Libro.anio}",
                fontSize = 18.sp,
                color = Color.White

            )
            Text(
                text = "Descripcion ${Libro.descripcion}",
                fontSize = 18.sp,
                color = Color.White

            )
            Text(
                text = "Disponibilidad ${Libro.disponible}",
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
                        onEditar(Libro.id)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = {
                        mostrarDialogo=true
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
            if(mostrarDialogo){
                AlertDialog(
                    onDismissRequest = {mostrarDialogo=false},
                    title ={
                        Text("Confirmacion")
                    },
                    text={
                        Text("Estas seguro de eliminar \"${Libro.titulo}\"?")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                mostrarDialogo=false
                                onEliminar(Libro)
                            }
                        ) {
                            Text("Elimiar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                mostrarDialogo=false
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