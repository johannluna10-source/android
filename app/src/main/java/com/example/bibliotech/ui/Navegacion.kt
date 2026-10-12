package com.example.bibliotech.ui

import android.app.Application

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.platform.LocalContext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.util.SesionManager
import com.example.bibliotech.viewmodel.EstudianteViewModel

import com.example.bibliotech.viewmodel.LibroViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navegacion(
    navController: NavHostController
) {
    /*
     * ---------------------------------------------------------
     * SESIÓN
     * ---------------------------------------------------------
     *
     * Antes de decidir qué pantalla mostrar primero,
     * comprobamos si existe una sesión guardada.
     *
     * Si existe:
     *     iniciamos directamente en "inicio".
     *
     * Si no existe:
     *     iniciamos en "login".
     */
    val contexto =
        androidx.compose.ui.platform.LocalContext.current

    val sesionManager =
        remember {
            SesionManager(contexto)
        }

    val tieneSesion =
        remember {
            sesionManager.sesionActiva()
        }

    /*
     * La ruta inicial depende de la sesión.
     *
     * Esto permite que el usuario no tenga que iniciar
     * sesión cada vez que abre la aplicación.
     */
    val rutaInicial =
        if (tieneSesion) {
            "inicio"
        } else {
            "login"
        }

    // =========================================================
    // MENSAJE GENERAL
    // =========================================================
    // Esta variable se utiliza para enviar mensajes entre
    // las pantallas.
    //
    // Ejemplos:
    // "✔ Libro guardado con éxito"
    // "✔ Libro eliminado con éxito"
    // "✔ Estudiante guardado con éxito"
    // =========================================================
    var mensaje by remember {
        mutableStateOf<String?>(null)
    }


    // =========================================================
    // NAVHOST
    // =========================================================
    NavHost(
        navController = navController,
        // **** AÑADIR EL PARAEMTRO
        startDestination = rutaInicial
    ) {

        // =====================================================
        // LOGIN
        // =====================================================

        composable("login") {

            PantallaLogin(

                onLoginExitoso = {

                    /*
                     * Después de iniciar sesión correctamente,
                     * vamos a la pantalla principal.
                     *
                     * popBackStack evita que el usuario pueda
                     * regresar al Login presionando Atrás.
                     */
                    navController.navigate("inicio") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onCrearCuenta = {

                    navController.navigate("registro")
                }
            )
        }
        // =====================================================
        // REGISTRO
        // =====================================================

        composable("registro") {

            PantallaRegistro(

                onRegistroExitoso = {

                    /*
                     * Después de crear la cuenta regresamos
                     * al Login.
                     *
                     * No iniciamos sesión automáticamente.
                     * El usuario tendrá que introducir sus
                     * credenciales en el Login.
                     */
                    navController.navigate("login") {

                        popUpTo("registro") {
                            inclusive = true
                        }
                    }
                },

                onRegresar = {

                    navController.popBackStack()
                }
            )
        }

        // =====================================================
        // PANTALLA PRINCIPAL
        // =====================================================
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
                onEstudiantes = {
                    navController.navigate("estudiantes")
                },
                // Mensaje enviado desde otras pantallas. // ------------- AQUI
                mensaje = mensaje,

                // Limpiamos el mensaje después de mostrarlo.
                onMensajeMostrado = {
                    mensaje = null
                },
                /*
                 * CERRAR SESIÓN
                 */
                onCerrarSesion = {

                    /*
                     * Eliminamos la sesión guardada.
                     */
                    sesionManager.cerrarSesion()

                    /*
                     * Regresamos al Login.
                     *
                     * También eliminamos "inicio" de la
                     * pila de navegación para que el usuario
                     * no pueda regresar a la pantalla principal
                     * utilizando el botón Atrás.
                     */
                    navController.navigate("login") {

                        popUpTo("inicio") {
                            inclusive = true
                        }
                    }
                },

                )
        }


        // =====================================================
        // CATÁLOGO DE LIBROS
        // =====================================================
        composable("catalogo") {

            PantallaCatalogo(

                onRegresar = {
                    navController.popBackStack()
                },

                onVerDetalles = { idLibro ->
                    navController.navigate("detalle/$idLibro")
                },

                onAgregarLibro = {
                    navController.navigate("agregar")
                },

                mensaje = mensaje,

                onMensajeMostrado = {
                    mensaje = null
                }
            )
        }


        // =====================================================
        // AGREGAR LIBRO
        // =====================================================
        composable("agregar") {

            PantallaAgregarLibro(

                viewModel = viewModel(),

                onGuardar = {

                    // Mensaje que recibirá PantallaCatalogo
                    mensaje = "✔ Libro guardado con éxito"

                    // Regresamos al catálogo
                    navController.popBackStack()
                },

                onCancelar = {
                    navController.popBackStack()
                }
            )
        }


        // =====================================================
        // DETALLE DE LIBRO
        // =====================================================
        composable("detalle/{idLibro}") {

            // Obtenemos el ID enviado mediante la ruta
            val idLibro =
                it.arguments
                    ?.getString("idLibro")
                    ?.toIntOrNull()


            // Obtenemos la aplicación
            val app =
                LocalContext.current.applicationContext
                        as BibliotecaApplication


            // Creamos el ViewModel de libros
            val viewModel: LibroViewModel = viewModel(

                factory = object : ViewModelProvider.Factory {

                    override fun <T : ViewModel> create(
                        modelClass: Class<T>
                    ): T {

                        return LibroViewModel(
                            app as Application
                        ) as T
                    }
                }
            )


            // Observamos el libro seleccionado
            val libro by viewModel
                .libroSeleccionado
                .collectAsState()


            // Cargamos el libro cuando cambia el ID
            LaunchedEffect(idLibro) {

                if (idLibro != null) {

                    viewModel.cargarLibroPorId(idLibro)
                }
            }


            // Si encontramos el libro mostramos el detalle
            if (libro != null) {

                PantallaDetalleLibro(

                    libro = libro!!,

                    onRegresar = {
                        navController.popBackStack()
                    },
                    navController = navController,

                    onEditar = { idLibro ->

                        // Enviamos el ID a la pantalla de edición
                        navController.navigate(
                            "editar/$idLibro"
                        )
                    },

                    onEliminar = { libroEliminar ->

                        // Eliminamos el libro mediante ViewModel
                        viewModel.eliminarLibro(
                            libroEliminar
                        )

                        // Mensaje que recibirá el catálogo
                        mensaje =
                            "✔ Libro eliminado con éxito"

                        // Regresamos al catálogo
                        navController.popBackStack()
                    }
                )
            }
        }


        // =====================================================
        // EDITAR LIBRO
        // =====================================================
        composable("editar/{idLibro}") {

            // Obtenemos el ID del libro
            val idLibro =
                it.arguments
                    ?.getString("idLibro")
                    ?.toIntOrNull()


            // Obtenemos la aplicación
            val app =
                LocalContext.current.applicationContext
                        as BibliotecaApplication


            // Creamos el ViewModel
            val viewModel: LibroViewModel = viewModel(

                factory = object : ViewModelProvider.Factory {

                    override fun <T : ViewModel> create(
                        modelClass: Class<T>
                    ): T {

                        return LibroViewModel(
                            app as Application
                        ) as T
                    }
                }
            )


            // Observamos el libro seleccionado
            val libro by viewModel
                .libroSeleccionado
                .collectAsState()


            // Cargamos el libro
            LaunchedEffect(idLibro) {

                if (idLibro != null) {

                    viewModel.cargarLibroPorId(idLibro)
                }
            }


            // Si existe el libro mostramos la pantalla
            // de edición
            if (libro != null) {

                PantallaEditarLibro(

                    libro = libro!!,

                    onGuardar = { libroEditado ->
                        viewModel.actualizarLibro(libroEditado)
                        // Enviamos el mensaje a la pantalla anterior (Detalle)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "mensaje",
                                "✓ Cambios guardados correctamente"
                            )
                        navController.popBackStack()
                    },

                    onCancelar = {
                        navController.popBackStack()
                    }
                )
            }
        }


        // =====================================================
        // ESTUDIANTES
        // =====================================================
        // Nueva ruta correspondiente al CRUD de estudiantes.
        //
        // Por ahora solamente conectamos la pantalla de
        // listado. Más adelante agregaremos:
        //
        // detalle/{idEstudiante}
        // agregarEstudiante
        // editarEstudiante/{idEstudiante}
        // =====================================================
        composable("estudiantes") {

            PantallaEstudiantes(

                onRegresar = {
                    navController.popBackStack()
                },

                onVerDetalles = { idEstudiante ->
                    navController.navigate("detalleEstudiante/$idEstudiante")
                },

                onAgregarEstudiante = {

                    navController.navigate("agregarEstudiante")
                },

                mensaje = mensaje,

                onMensajeMostrado = {
                    mensaje = null
                }
            )
        }
        composable("agregarEstudiante") {

            val app =
                LocalContext.current.applicationContext as BibliotecaApplication

            val viewModel: EstudianteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return EstudianteViewModel(app as Application) as T
                    }
                }
            )

            PantallaAgregarEstudiante(
                viewModel = viewModel,
                onGuardar = {
                    mensaje = "✔ Estudiante guardado con éxito"
                    navController.popBackStack()
                },
                onCancelar = {
                    navController.popBackStack()
                }
            )
        }
        composable("detalleEstudiante/{idEstudiante}") {

            val idEstudiante = it.arguments
                ?.getString("idEstudiante")
                ?.toIntOrNull()

            val app =
                LocalContext.current.applicationContext as BibliotecaApplication

            val viewModel: EstudianteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>
                    ): T {
                        return EstudianteViewModel(app as Application) as T
                    }
                }
            )

            val estudiante by viewModel
                .estudianteSeleccionado
                .collectAsState()

            LaunchedEffect(idEstudiante) {
                if (idEstudiante != null) {
                    viewModel.cargarEstudiantePorId(idEstudiante)
                }
            }

            if (estudiante != null) {

                PantallaDetalleEstudiante(
                    estudiante = estudiante!!,
                    onRegresar = {
                        navController.popBackStack()
                    },
                    navController = navController,
                    onEditar = { id ->
                        navController.navigate("editarEstudiante/$id")
                    },
                    onEliminar = { estudianteEliminar ->

                        viewModel.eliminarEstudiante(estudianteEliminar)
                        mensaje = "✔ Estudiante eliminado con éxito"
                        navController.popBackStack()
                    }
                )
            }
        }


        composable("editarEstudiante/{idEstudiante}") {

            val idEstudiante = it.arguments
                ?.getString("idEstudiante")
                ?.toIntOrNull()

            val app =
                LocalContext.current.applicationContext as BibliotecaApplication

            val viewModel: EstudianteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>
                    ): T {
                        return EstudianteViewModel(app as Application) as T
                    }
                }
            )

            val estudiante by viewModel
                .estudianteSeleccionado
                .collectAsState()

            LaunchedEffect(idEstudiante) {
                if (idEstudiante != null) {
                    viewModel.cargarEstudiantePorId(idEstudiante)
                }
            }

            if (estudiante != null) {

                PantallaEditarEstudiante(

                    estudiante = estudiante!!,

                    onGuardar = { estudianteEditado ->

                        viewModel.actualizarEstudiante(estudianteEditado)

                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "mensaje",
                                "✓ Cambios guardados correctamente"
                            )

                        navController.popBackStack()
                    },

                    onCancelar = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // =====================================================
        // PRÉSTAMOS
        // =====================================================
        composable("prestamo") {

            PantallaPrestamo(

                onRegresar = {
                    navController.popBackStack()
                },

                onPrestamoGuardado = {

                    // Mensaje que recibirá la pantalla anterior
                    mensaje = "✔ Préstamo registrado con éxito"

                    // Regresamos a la pantalla anterior
                    navController.popBackStack()
                }
            )
        }


        // =====================================================
        // LIBROS PRESTADOS
        // =====================================================
        composable("prestados") {

            PantallaLibrosPrestados(

                onRegresar = {
                    navController.popBackStack()
                }
            )
        }


    }
}