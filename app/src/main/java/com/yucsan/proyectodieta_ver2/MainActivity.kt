package com.yucsan.proyectodieta_ver2

import BD_Fichero_Android
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examenjpc_1.screens.Formulario
import com.example.examenjpc_1.screens.Inicio
import com.example.examenjpc_1.screens.ListadoDetalle
import com.yucsan.proyectodieta_ver2.CompoNav.NavigationBar
import com.yucsan.proyectodieta_ver2.componentes.DrawerContent
import com.yucsan.proyectodieta_ver2.componentes.MiTopAppBar
import com.yucsan.proyectodieta_ver2.screens.Ruta
import com.yucsan.proyectodieta_ver2.ui.theme.ProyectoDieta_ver2Theme
import kotlinx.coroutines.launch
import modelo.CDModelView
import modelo.DaoCD
import modelo.DaoIngrediente
import modelo.TipoComponente


class MainActivity : ComponentActivity() {

   val viewModelCD: CDModelView by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         val context = LocalContext.current
         val bdFichero: BD_Fichero_Android=BD_Fichero_Android(context, "Componente")

         // variables Navegación
         val navigationController = rememberNavController()
         val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
         val scope = rememberCoroutineScope()

         // Opciones Formulario
         val opciones = listOf(TipoComponente.SIMPLE, TipoComponente.PROCESADO)

         ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
               DrawerContent { route ->
                  scope.launch { drawerState.close() }
                  navigationController.navigate(route)
               }
            }
         ) {
            ProyectoDieta_ver2Theme {
               Scaffold(

                  topBar = {  MiTopAppBar(onMenuClick = { scope.launch { drawerState.open() } })  },
                  bottomBar = { NavigationBar(navigationController)}

               ) { paddingValues ->
                  NavHost(
                     navController = navigationController,
                     startDestination = Ruta.Pantalla1.ruta,
                     modifier = Modifier.padding(paddingValues)
                  ) {
                     composable(Ruta.Pantalla1.ruta) {
                        Inicio(viewModelCD, context)
                     }
                     composable(Ruta.Pantalla2.ruta) {
                        Formulario(navigationController, opciones, viewModelCD )
                     }
                     composable(Ruta.Pantalla3.ruta) {
                        ListadoDetalle(navigationController, opciones, viewModelCD, context)
                     }
                  }
               }
            }
         }
      }
   }
}




