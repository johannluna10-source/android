package com.example.bibliotech.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.model.Libro
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.bibliotech.R
import androidx.compose.ui.res.painterResource

/*
Componente reutilizable que estamos utilizando
mostrar la informacion de cada libro dentro de una card
 */

@Composable
fun TarjetaLibro(Libro:Libro, onVerDetalles:()-> Unit){

    Card(modifier= Modifier.fillMaxWidth()){
        Column(modifier = Modifier.padding(6.dp)){

            //Row: permite colocar una fila en ella ira el icono
            //y la informcion
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Gato negro en marco café",
                    modifier = Modifier.size(80.dp),
                    //hace que mantenga sus colores reales
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Text(
                    text= Libro.titulo, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Autor: ${Libro.autor}")
            }

}
            Button(onClick = onVerDetalles,
                modifier= Modifier.align(Alignment.End).padding(6.dp)
            ) {
                Text("Ver detalles")
            }


        }


    }


