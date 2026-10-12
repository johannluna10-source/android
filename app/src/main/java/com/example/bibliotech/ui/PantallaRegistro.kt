package com.example.bibliotech.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliotech.viewmodel.UsuarioViewModel

@Composable
fun PantallaRegistro(
    onRegistroExitoso: () -> Unit,
    onRegresar: () -> Unit,
    viewModel: UsuarioViewModel = viewModel()
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var usuario by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    val registroExitoso by
    viewModel.registroExitoso.collectAsState()

    val usuarioExiste by
    viewModel.usuarioExiste.collectAsState()

    LaunchedEffect(registroExitoso) {

        if (registroExitoso) {

            viewModel.reiniciarEstado()

            onRegistroExitoso()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Crear cuenta"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = {
                usuario = it
            },
            label = {
                Text("Usuario")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
            },
            label = {
                Text("Contraseña")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (usuarioExiste) {

            Text(
                text =
                    "El usuario ya existe. " +
                            "Elige otro nombre de usuario."
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        Button(
            onClick = {

                if (
                    nombre.isNotBlank() &&
                    usuario.isNotBlank() &&
                    contrasena.isNotBlank()
                ) {

                    viewModel.registrarUsuario(
                        nombre = nombre.trim(),
                        usuario = usuario.trim(),
                        contrasena = contrasena
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Crear cuenta"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        TextButton(
            onClick = onRegresar,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Ya tengo una cuenta"
            )
        }
    }
}