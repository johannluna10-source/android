package com.example.bibliotech.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarEstudiante(
    viewModel: EstudianteViewModel,
    onGuardar:()-> Unit,
    OnCancelar:()-> Unit
){
    var carnet by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }

    val grados =listOf(
        "1° Bachillerato",
        "2° Bachillerato",
        "3° Bachillerato"

    )
    var grado by remember { mutableStateOf(grados[0]) }
    var expandirGrado by remember { mutableStateOf(false) }

    val secciones =listOf("A","B","C")
    var seccion by remember {mutableStateOf(secciones[0])}
    var expandirSeccion by remember { mutableStateOf(false) }

    var activo by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Text("Agregar Estudiante",color = Color.White)
                },
                colors= TopAppBarDefaults.topAppBarColors(
                    containerColor= Color.Black
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

        }
    }
}