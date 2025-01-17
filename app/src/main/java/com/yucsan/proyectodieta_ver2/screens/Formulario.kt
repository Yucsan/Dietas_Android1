package com.example.examenjpc_1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yucsan.proyectodieta_ver2.componentes.MiRadioButton
import com.yucsan.proyectodieta_ver2.componentes.MisTabsMain
import modelo.CDModelView
import modelo.ComponenteDieta
import modelo.TipoComponente

@Composable
fun Formulario(
   //lista: SnapshotStateList<ComponenteDieta>,
   navController: NavController,
   opcionesRadio: List<TipoComponente>,
   view: CDModelView
   ) {

   var componenteDieta by remember { mutableStateOf(ComponenteDieta()) }
   
   var nombre by remember { mutableStateOf("") }
   var grProt by remember { mutableStateOf("") }
   var grHC by remember { mutableStateOf("") }
   var grLip by remember { mutableStateOf("") }
   //var text5 by remember { mutableStateOf("") }

   var esSimple = remember { mutableStateOf(true) }

   var seleccion = remember { mutableStateOf<TipoComponente>(TipoComponente.SIMPLE) }

   //val context = LocalContext.current
   LazyColumn {
      item {
         Column {
            Box(modifier = Modifier.fillMaxWidth()) {
               Column() {
                  MiRadioButton(opcionesRadio, seleccion, esSimple)  // RADIO BUTTON
                  Text( modifier = Modifier.padding(8.dp), //TEXTO KCALORIAS
                        text = ""+componenteDieta.Kcal+" calorias" )
                  TextField(
                     value = nombre,
                     onValueChange = { newText ->
                        componenteDieta.nombre = newText
                        nombre = newText
                     },
                     label = { Text("Escribe nombre") },
                     placeholder = { Text("nombre") },
                     modifier = Modifier.fillMaxWidth()
                  )

                  if(esSimple.value){
                     TextField(
                        value = grProt,
                        onValueChange = { newText ->
                           componenteDieta.grPro_ini = newText.toDoubleOrNull() ?: 0.0
                           grProt = newText
                        },
                        label = { Text("Cantidad de proteínas x 100grs") },
                        placeholder = { Text("Cantidad") },
                        modifier = Modifier.fillMaxWidth()
                     )
                     TextField(
                        value = grHC,
                        onValueChange = { newText ->
                           componenteDieta.grHC_ini = newText.toDoubleOrNull() ?: 0.0
                           grHC = newText
                        },
                        label = { Text("Hidratos de carbono x 100grs") },
                        placeholder = { Text("Hidratos de carbono") },
                        modifier = Modifier.fillMaxWidth()
                     )
                     TextField(
                        value = grLip,
                        onValueChange = { newText ->
                           componenteDieta.grLip_ini = newText.toDoubleOrNull() ?: 0.0
                           grLip = newText
                        },
                        label = { Text("Cantidad de Lípidos x 100grs") },
                        placeholder = { Text("Lípidos") },
                        modifier = Modifier.fillMaxWidth()
                     )
                  }
                  Row(
                     horizontalArrangement = Arrangement.SpaceAround,
                     modifier=Modifier.fillMaxWidth()
                  ){

                     Button(
                        onClick = {
                           val  nuevoComponenteDieta = ComponenteDieta(
                              nombre = nombre,
                              tipo = seleccion.value,
                              grPro_ini = grProt.toDoubleOrNull() ?: 0.0,
                              grHC_ini = grHC.toDoubleOrNull() ?: 0.0,
                              grLip_ini = grLip.toDoubleOrNull() ?: 0.0
                           )
                           view.añadirComponente(nuevoComponenteDieta)
                           nombre = ""; grProt = ""; grHC = ""; grLip = "" //Vaciado de Variables
                        }
                     ) { Text(text = "Aceptar") } //Al hacer click se guarda ese nombre en el listado

                     Button(
                        onClick = {}
                     ) { Text("Reset") }

                  }


               }
            }
         }
      }
   }

}




