package com.example.bibliotech.ui

import android.app.Application
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.data.LibrosPrueba
import com.example.bibliotech.ui.componentes.TarjetaLibro
import com.example.bibliotech.model.Libro
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotech.viewmodel.LibroViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PantallaCatalogo(
    onRegresar: () -> Unit,
    onVerDetalles: (Int) -> Unit,
    onAgregarLibro: () -> Unit,
    mensaje: String?,
    onMensajeMostrado: () -> Unit
) {
    val app = LocalContext.current.applicationContext as BibliotecaApplication
    val viewModel : LibroViewModel = viewModel(
        factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel >create(modelClass:Class<T>): T{
                return LibroViewModel(app as Application) as T
            }
        }
    )
    val libros by viewModel.libros.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope= rememberCoroutineScope ()
    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }
    LaunchedEffect(mensaje) {
        if(mensaje != null){
            snackbarHostState.showSnackbar(mensaje)
            onMensajeMostrado()
        }
    }
var textoBusqueda by remember { mutableStateOf("") }
    val categoria = listOf("Todas","Literatura","Novela","Programacion" )
    var categoriaSeleccionada by remember { mutableStateOf("Todas") }
    val librosFiltrados = libros.filter { libro ->
        val coincideTexto = libro.titulo.contains(textoBusqueda,
            ignoreCase = true) || libro.autor.contains(textoBusqueda,
                ignoreCase = true)
        val coincideCategoria = categoriaSeleccionada =="Todas"||
                libro.categoria == categoriaSeleccionada
        coincideTexto && coincideCategoria

    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Start,

        floatingActionButton =
            {
                FloatingActionButton(onClick = onAgregarLibro, containerColor = Color.Black) {
                    Text("+", color= Color.White)
                }
            },
        topBar = {
        TopAppBar(title = {Text("Catalogo de libros")})
    }){
        padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(5.dp)
                .fillMaxSize()

        ) {

            OutlinedTextField(value= textoBusqueda, onValueChange = {textoBusqueda = it}, label={Text("Buscar libro o autor")}, modifier=Modifier.fillMaxWidth(), textStyle = TextStyle(color = Color.White))
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.horizontalScroll(state = rememberScrollState())) {
                categoria.forEach { categoria ->
                    FilterChip(selected =categoriaSeleccionada == categoria,
                        onClick={categoriaSeleccionada=categoria},
                        label={
                            Text(categoria)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            if(librosFiltrados.isEmpty()){
                Column(modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Gato negro en marco café",
                        modifier = Modifier.size(80.dp),
                        //hace que mantenga sus colores reales
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "No se encontraron libros",
                        fontWeight = FontWeight.Bold
                    )
                }

            }else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(librosFiltrados) { libro ->
                        TarjetaLibro(
                            Libro = libro,


                            onVerDetalles = {
                                onVerDetalles(libro.id)
                            }
                        )
                    }
                }
            }

        }
    }

    }


