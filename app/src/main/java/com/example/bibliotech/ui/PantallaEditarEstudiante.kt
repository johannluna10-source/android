package com.example.bibliotech.ui

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bibliotech.model.Estudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarEstudiante(
    estudiante:Estudiante,
    onGuardar:(Estudiante)->Unit,
    onCancelar:()->Unit
){
    //VARIABLES DEL FORMATO
    var carnet by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    //GRADO
    val grados = listOf(
        "1° Bachillerato",
        "2° Bachillerato",
        "3° Bachillerato"
    )
    var grado by remember { mutableStateOf(estudiante.grado) }
    var expandirGrado by remember { mutableStateOf(false) }

    //------SECCIÓN--------
    val secciones = listOf("A", "B", "C")
    var seccion by remember { mutableStateOf(secciones[0]) }
    var expandirSeccion by remember { mutableStateOf(false) }

    //-------ESTADO----------
    var activo by remember { mutableStateOf(true) }

    Scaffold(containerColor=Color.Black,
        topBar={
            TopAppBar(
                title={
                    Text("Editar Estudiante", color=Color.White)
                },
                colors= TopAppBarDefaults.topAppBarColors(
                    containerColor=Color.Black
                )
            )
        }){ padding ->
        Column(modifier= Modifier.padding(padding)
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(20.dp))
        {
            OutlinedTextField(
                value = carnet,
                onValueChange={carnet=it},
                label={Text("Carnet")},
                modifier=Modifier.fillMaxWidth(),
                colors= OutlinedTextFieldDefaults.colors(
                    focusedBorderColor=Color.Yellow,
                    unfocusedBorderColor=Color.LightGray,
                    focusedTextColor=Color.White,
                    unfocusedTextColor=Color.White,
                    focusedLabelColor=Color.Yellow,
                    cursorColor=Color.White
                )
            )
            Spacer (modifier=Modifier.height(12.dp))
            OutlinedTextField(
                value = nombres,
                onValueChange={nombres=it},
                label={Text("Carnet")},
                modifier=Modifier.fillMaxWidth(),
                colors= OutlinedTextFieldDefaults.colors(
                    focusedBorderColor=Color.Yellow,
                    unfocusedBorderColor=Color.LightGray,
                    focusedTextColor=Color.White,
                    unfocusedTextColor=Color.White,
                    focusedLabelColor=Color.Yellow,
                    cursorColor=Color.White
                )
            )
            Spacer (modifier=Modifier.height(12.dp))
            Spacer (modifier=Modifier.height(12.dp))
            OutlinedTextField(
                value = apellidos,
                onValueChange={apellidos=it},
                label={Text("Carnet")},
                modifier=Modifier.fillMaxWidth(),
                colors= OutlinedTextFieldDefaults.colors(
                    focusedBorderColor=Color.Yellow,
                    unfocusedBorderColor=Color.LightGray,
                    focusedTextColor=Color.White,
                    unfocusedTextColor=Color.White,
                    focusedLabelColor=Color.Yellow,
                    cursorColor=Color.White
                )
            )
            Spacer (modifier=Modifier.height(12.dp))
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
                        unfocusedTextColor = Color.LightGray,
                        focusedBorderColor = Color.Yellow,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.Yellow,
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
                        unfocusedTextColor = Color.LightGray,
                        focusedBorderColor = Color.Yellow,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.Yellow,
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
            Row{
                Checkbox(
                    checked=activo,
                    onCheckedChange={activo=it}
                )
                Text(
                    text="Activo",
                    color=Color.White,
                    modifier=Modifier.padding(top=8.dp)
                )
            }
            Spacer(modifier=Modifier.padding(10.dp))
            Button(
                onClick={
                    val estudianteEditado=estudiante.copy(
                        carnet=carnet,
                        nombres=nombres,
                        apellidos=apellidos,
                        grado=grado,
                        seccion=seccion,
                        activo=activo
                    )
                    onGuardar(estudianteEditado)
                },
                modifier=Modifier.fillMaxWidth()
            ){Text("Guardar Cambios")}
            Spacer(modifier=Modifier.padding(10.dp))
            OutlinedButton(
                onClick={
                    onCancelar()
                },
                modifier=Modifier.fillMaxWidth()
            ){Text("Cancelar")}

        }

    }


}
