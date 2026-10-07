package com.example.bibliotech.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.ui.componentes.BotonMenu
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.SnackbarHost


@Composable
fun PantallaPrincipal(
    onCatalogo: () -> Unit,
    onPrestamo: () -> Unit,
    onPrestados: () -> Unit,
    // NUEVO:
    // Función que permitirá abrir la pantalla de estudiantes.
    onEstudiantes: () -> Unit,
// --------------------------------------------------------------------------
    // Mensaje que recibe desde Navegacion.
    mensaje: String?,


    // Función para avisar que el mensaje ya fue mostrado.
    onMensajeMostrado: () -> Unit
) {
    // ---------------------------------------------------------
    // ESTADO DEL SNACKBAR
    // ---------------------------------------------------------
    val snackbarHostState =
        remember {
            SnackbarHostState()
        }
    // ---------------------------------------------------------
    // MOSTRAR MENSAJE
    // ---------------------------------------------------------


    LaunchedEffect(mensaje) {


        if (mensaje != null) {


            snackbarHostState.showSnackbar(
                message = mensaje
            )


            // Indicamos que el mensaje ya fue mostrado.
            onMensajeMostrado()
        }
    }
    Scaffold(


        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }


    ) { paddingValues ->


        Column(
            modifier = Modifier.padding(16.dp).padding(paddingValues) //MODIFIQE AQUI
        ) {


            // ============================================
            // ENCABEZADO
            // ============================================
            Text(
                text = "BiblioTechV2",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = "Sistema de Biblioteca Escolar",
                fontSize = 16.sp
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Consulta libros y administra los préstamos de la biblioteca escolar."
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )




            // ============================================
            // CATÁLOGO DE LIBROS
            // ============================================
            BotonMenu(
                texto = "Catálogo de libros",
                onClick = onCatalogo
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )




            // ============================================
            // ESTUDIANTES
            // ============================================
            BotonMenu(
                texto = "Estudiantes",
                onClick = onEstudiantes
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )




            // ============================================
            // REGISTRAR PRÉSTAMO
            // ============================================
            BotonMenu(
                texto = "Registrar préstamo",
                onClick = onPrestamo
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )




            // ============================================
            // LIBROS PRESTADOS
            // ============================================
            BotonMenu(
                texto = "Libros prestados",
                onClick = onPrestados
            )
        }
    }}

