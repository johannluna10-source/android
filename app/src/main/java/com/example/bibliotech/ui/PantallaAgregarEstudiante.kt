package com.example.bibliotech.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.bibliotech.viewmodel.EstudianteViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import com.example.bibliotech.model.Estudiante
import androidx.compose.foundation.layout.height

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarEstudiante(
    viewModel: EstudianteViewModel,
    onGuardar:()-> Unit,
    OnCancelar:()-> Unit
) {
    var carnet by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }

    val grados = listOf(
        "1° Bachillerato",
        "2° Bachillerato",
        "3° Bachillerato"

    )
    var grado by remember { mutableStateOf(grados[0]) }
    var expandirGrado by remember { mutableStateOf(false) }

    val secciones = listOf("A", "B", "C")
    var seccion by remember { mutableStateOf(secciones[0]) }
    var expandirSeccion by remember { mutableStateOf(false) }

    var activo by remember { mutableStateOf(false) }

    Scaffold(

        containerColor = Color.Black,
        topBar = {

            TopAppBar(

                title = {

                    Text("Agregar Estudiante", color = Color.White)

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )


            )


        }
    ) {
            paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ){
            //Carnet del estudiante
            OutlinedTextField(
                value = carnet,
                onValueChange = { carnet = it },
                label = { Text("Número de Carnet") },
                modifier = Modifier.fillMaxSize(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color.LightGray
                )

            )

            Spacer(modifier = Modifier.height(10.dp))

            //nombres del estudiante
            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Nombres del estudiante") },
                modifier = Modifier.fillMaxSize(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color.LightGray
                )

            )

            Spacer(modifier = Modifier.height(10.dp))


            //Apellidos del estudiante
            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos del estudiante") },

                modifier = Modifier.fillMaxSize(),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color.LightGray
                )

            )

            Spacer(modifier = Modifier.height(10.dp))


            //Grado del estudiante
            ExposedDropdownMenuBox(
                expanded = expandirGrado,
                onExpandedChange = { expandirGrado = !expandirGrado }
            )
            {
                OutlinedTextField(
                    value = grado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grado") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandirGrado
                        )

                    },
                    modifier = Modifier.fillMaxSize().menuAnchor(),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF3B82F6),
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color(0xFF60A5FA),
                        unfocusedLabelColor = Color.LightGray
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirGrado,
                    onDismissRequest = { expandirGrado = false }
                ) {
                    grados.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                grado = it
                                expandirGrado = false
                            }

                        )
                    }
                }


            }

            Spacer(modifier = Modifier.height(10.dp))

            //Seccion del estudiante
            ExposedDropdownMenuBox(
                expanded = expandirSeccion,
                onExpandedChange = { expandirSeccion = !expandirSeccion }
            )
            {
                OutlinedTextField(
                    value = seccion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seccion") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandirSeccion
                        )

                    },
                    modifier = Modifier.fillMaxSize().menuAnchor(),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF3B82F6),
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color(0xFF60A5FA),
                        unfocusedLabelColor = Color.LightGray
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandirSeccion,
                    onDismissRequest = { expandirSeccion = false }
                ) {
                    secciones.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                seccion = it
                                expandirSeccion = false
                            }

                        )
                    }
                }


            }

            Spacer(modifier = Modifier.height(10.dp))
            //Estado del estudiante
            Row{
                Checkbox(
                    checked = activo,
                    onCheckedChange = {activo = it}
                )

                Text(
                    text = "Activo",
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Botones

            Button(onClick = {
                val nuevoEstudiante = Estudiante(
                    carnet = carnet,
                    nombres = nombres,
                    apellidos = apellidos,
                    grado = grado,
                    seccion = seccion,
                    activo = activo
                )
                viewModel.insertarEstudiante(nuevoEstudiante)
                onGuardar()
            },
                modifier = Modifier.fillMaxSize()
            ){
                Text("Guardar Estudiante")

            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                OnCancelar()
            },
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Cancelar")

            }


        }

    }

}