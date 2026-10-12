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
fun PantallaLogin(
    onLoginExitoso: () -> Unit,
    onCrearCuenta: () -> Unit,
    viewModel: UsuarioViewModel = viewModel()
) {

    var usuario by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    val loginExitoso by
    viewModel.loginExitoso.collectAsState()

    val loginIncorrecto by
    viewModel.loginIncorrecto.collectAsState()

    // Cuando el ViewModel confirma que las
    // credenciales son correctas, entramos
    // a la pantalla principal.
    LaunchedEffect(loginExitoso) {

        if (loginExitoso) {

            viewModel.reiniciarEstado()

            onLoginExitoso()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "BiblioTech"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Iniciar sesión"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Campo para escribir el nombre de usuario.
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

        // Campo para escribir la contraseña.
        // PasswordVisualTransformation evita
        // mostrar la contraseña directamente.
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
            modifier = Modifier.height(12.dp)
        )

        // Mensaje cuando las credenciales son incorrectas.
        if (loginIncorrecto) {

            Text(
                text =
                    "Usuario o contraseña incorrectos."
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        // Botón para iniciar sesión.
        Button(
            onClick = {

                if (
                    usuario.isNotBlank() &&
                    contrasena.isNotBlank()
                ) {

                    viewModel.iniciarSesion(
                        usuario = usuario.trim(),
                        contrasena = contrasena
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Iniciar sesión"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // Si el usuario todavía no tiene cuenta,
        // puede dirigirse al registro.
        TextButton(
            onClick = onCrearCuenta,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "¿No tienes cuenta? Crear una"
            )
        }
    }
}