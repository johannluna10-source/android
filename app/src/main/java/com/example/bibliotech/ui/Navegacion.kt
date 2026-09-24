package com.example.bibliotech.ui

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.data.LibrosPrueba
import com.example.bibliotech.model.Libro
import com.example.bibliotech.viewmodel.LibroViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import com.example.bibliotech.viewmodel.EstudianteViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Navegacion(
    navController: NavHostController
) {

    var mensaje by remember { mutableStateOf<String?>(null) }
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        composable("inicio") {

            PantallaPrincipal(
                onCatalogo = {
                    navController.navigate("catalogo")
                },
                onPrestamo = {
                    navController.navigate("prestamo")
                },
                onPrestados = {
                    navController.navigate("prestados")
                },
                onEstudiante = {
                    navController.navigate("estudiantes")
                }
            )
        }

        composable("catalogo") {
            PantallaCatalogo(

                onRegresar = {
                    navController.popBackStack()
                },

                onVerDetalles = { idLibro ->
                    navController.navigate("detalle/$idLibro")
                },
                onAgregarLibro ={navController.navigate("agregar")},
                mensaje=mensaje,
                onMensajeMostrado={mensaje=null}

            )
        }
        composable("agregar"){
            PantallaAgregarLibro(

                onGuardar={
                    mensaje="Libro guardado con exito"
                    navController.popBackStack()


                },
                onCancelar={
                    navController.popBackStack()
                },
                viewModel = viewModel()
            )
        }
        composable("detalle/{idLibro}") {

            val idLibro = it.arguments
                ?.getString("idLibro")
                ?.toIntOrNull()

           val app = LocalContext.current.applicationContext as BibliotecaApplication

            val viewModel: LibroViewModel = viewModel(
                factory= object : ViewModelProvider.Factory{
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>
                    ): T {
                        return LibroViewModel(app as Application) as T
                    }
                }
            )

            val libro by viewModel.libroSeleccionado.collectAsState()
            //#
            LaunchedEffect(idLibro) {
                if(idLibro != null){
                    viewModel.cargarLibroPorId(idLibro)
                }
            }
            if (idLibro != null && libro != null) {
                PantallaDetalleLibro(
                    Libro = libro!!,
                    onRegresar = { navController.popBackStack() },

                    navController = navController,

                    onEditar={
                        idLibro -> navController.navigate("editar/$idLibro")
                    },
                    onEliminar = {
                        libroEliminar-> viewModel.eliminarLibro(libroEliminar)
                        mensaje="Libro eliminado con exito"
                        navController.popBackStack()
                    }
                )
            }

        }

        composable  ("editar/{idLibro}") {
            val idLibro = it.arguments?.getString("idLibro")?.toIntOrNull()
            val app = LocalContext.current.applicationContext as BibliotecaApplication
            val viewModel: LibroViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>


                    ): T {
                        return LibroViewModel(app as Application) as T
                    }
                }
            )

            val libro by viewModel.libroSeleccionado.collectAsState()
            LaunchedEffect(idLibro) {
                if(idLibro!=null){
                    viewModel.cargarLibroPorId(idLibro)
                }

            }
            if (libro!=null) {
                PantallaEditarLibro(
                    libro = libro!!,
                    onGuardar = { libroEditado ->
                        viewModel.actualizarLibro(libroEditado)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "Mensaje",
                                "Cambios guardados correctamente"
                            )
                        navController.popBackStack()
                    },
                    onCancelar = {
                        navController.popBackStack()
                    }
                )
            }

        }


        composable("prestamo") {

            PantallaPrestamo   (
                onRegresar = {
                    navController.popBackStack()
                }
            )
        }

        composable("estudiantes") {

            PantallaEstudiantes   (
                onRegresar = {
                    navController.popBackStack()
                },
                onVerDetalles = {},
                onAgregarEstudiante = {
                    navController.navigate("agregarEstudiante")
                },
                mensaje = mensaje,
                onMensajeMostrado = {mensaje = null})
        }




        composable("prestados") {

            PantallaLibrosPrestados(
                onRegresar = {
                    navController.popBackStack()
                }
            )
        }

        composable("agregarEstudiantes") {

            val app = LocalContext.current.applicationContext as BibliotecaApplication
            val viewModel: EstudianteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>


                    ): T {
                        return EstudianteViewModel(app as Application) as T
                    }
                }
            )
            PantallaAgregarEstudiante(
                viewModel=viewModel(),
                onGuardar ={
                    mensaje ="Estudiante guardado con exito"
                    navController.popBackStack()
                },
                OnCancelar={
                    navController.popBackStack()
                }
            )
        }
    }
}
