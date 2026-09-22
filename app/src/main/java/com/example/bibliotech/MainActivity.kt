package com.example.bibliotech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.bibliotech.ui.Navegacion
import com.example.bibliotech.ui.theme.BiblioTechTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.bibliotech.data.LibrosPrueba


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        val app = application as BibliotecaApplication
        val repository = app.libroRepository

        lifecycleScope.launch(Dispatchers.IO) {

            if (repository.obtenerLibros().isEmpty()) {

                LibrosPrueba.forEach { Libro ->
                    repository.insertarLibro(Libro)
                }
            }
        }

        setContent {

            BiblioTechTheme {

                val navController = rememberNavController()

                Navegacion(
                    navController = navController
                )
            }
        }

    }
}

