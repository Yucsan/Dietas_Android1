package com.example.examenjpc_1.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yucsan.proyectodieta_ver2.Componentes2.CartaIngredientes
import com.yucsan.proyectodieta_ver2.Componentes2.MiCarta
import modelo.CDModelView

import modelo.ComponenteDieta
import modelo.Ingrediente


@Composable
fun ListadoIngredientes(
   modeloVista: CDModelView,
   componente: ComponenteDieta,
   context: Context
) {
   // Lista de componentes dieta observada desde el ViewModel
   val componentesView by modeloVista.componentes.observeAsState(emptyList())
   val ingredientesLista = remember { mutableStateListOf(*componente.ingredientes.toTypedArray()) }

   val isCheckedList = remember(componentesView) {
      mutableStateListOf(*Array(componentesView.size) { false })
   }

   val muestraCantidad = remember(componentesView) {
      mutableStateListOf(*Array(componentesView.size) { " " }) // array de cantidad string x formulario
   }
   val nuevaCantidad = remember(componentesView) {
      mutableStateListOf(*Array(componentesView.size) { 0.0 }) // array de cantidad double x componenteDieta del dataClass
   }

   Box(modifier = Modifier.fillMaxSize()) {
      Column {
         Text(text = "Agregar Ingredientes")
         Text(fontSize = 14.sp, color= Color.Magenta,
            text = componente.nombre )



         // Lógica para mostrar los ingredientes actuales
         LazyColumn {
            if (ingredientesLista.isNotEmpty()) {
               itemsIndexed(ingredientesLista) { index, ingrediente ->
                  Row {
                     Text(text = "${ingrediente.cd.nombre } (${ingrediente.cantidad})")
                     Spacer(modifier = Modifier.width(8.dp))
                     Button(onClick = {
                        // Elimina el ingrediente de la lista directamente
                        ingredientesLista.removeAt(index)
                        componente.removeIngrediente(ingrediente)

                        modeloVista.guardarDatos(context)

                     }) { Text("Eliminar") }
                  }
               }
            } else {
               item {
                  Text("No hay Ingredientes")
               }
            }
         }

         // Mostrar listado de componentes dieta disponibles para agregar como ingredientes
         LazyColumn {
            if (componentesView.isNotEmpty()) {
               itemsIndexed(componentesView) { index, compo ->
                  Row {
                     Checkbox(
                        checked = isCheckedList[index],
                        onCheckedChange = { isChecked ->
                           isCheckedList[index] = isChecked
                           // Acciones adicionales si es necesario
                           if (isChecked) {
                              //--------------------------------------------------------------------

                              val nuevoIngrediente = Ingrediente(compo, nuevaCantidad[index])
                              if (componente.addIngrediente(nuevoIngrediente) ) {
                                 ingredientesLista.add(nuevoIngrediente)
                              }
                              modeloVista.guardarDatos(context)
                           }
                        }
                     )
                     Text(text = componentesView[index].nombre)

                     TextField(
                        value = muestraCantidad[index],
                        onValueChange = { newText ->
                           nuevaCantidad[index] = newText.toDoubleOrNull() ?: 0.0
                           muestraCantidad[index] = newText
                        },
                        label = { Text("Cantidad") },
                        placeholder = { Text("") },
                        modifier = Modifier.fillMaxWidth()
                     )

                  }
               }
            }
         }
      }
   }
}



