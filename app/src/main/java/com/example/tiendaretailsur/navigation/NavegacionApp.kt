package com.example.tiendaretailsur.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tiendaretailsur.ui.screens.AdminDashboardScreen
import com.example.tiendaretailsur.ui.screens.FormularioConteoScreen
import com.example.tiendaretailsur.ui.screens.FormularioRegistroScreen
import com.example.tiendaretailsur.ui.screens.LoginScreen
import com.example.tiendaretailsur.ui.screens.MenuPrincipalScreen
import com.example.tiendaretailsur.ui.screens.RecuperarContrasenaScreen
import com.example.tiendaretailsur.ui.theme.CardOscura
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.ui.theme.TextoOscuro

object Rutas {
    const val PRUEBAS = "pruebas"
    const val LOGIN = "login"
    const val RECUPERAR_CONTRASENA = "recuperar_contrasena"
    const val MENU_PRINCIPAL = "menu_principal"
    const val FORM_CONTEO = "formulario_conteo"
    const val FORM_REGISTRO = "formulario_registro"
    const val ADMIN_DASHBOARD = "admin_dashboard"
    const val GESTION_USUARIOS = "gestion_usuarios"
    const val AJUSTES = "ajustes"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionApp(initialDarkTheme: Boolean = isSystemInDarkTheme()) {
    var isAdmin by rememberSaveable { mutableStateOf(true) }
    var isDarkTheme by rememberSaveable { mutableStateOf(initialDarkTheme) }

    val navController = rememberNavController()
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    // El dashboard y el Login ya traen su propio diseño, la barra de pruebas se oculta allí
    val mostrarBarraPruebas = rutaActual != Rutas.ADMIN_DASHBOARD

    val navContainerColor = CardOscura
    val unselectedColor = TextoOscuro

    MiRegistroTheme(darkTheme = isDarkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (mostrarBarraPruebas) {
                    TopAppBar(
                        title = {
                            Text(if (isAdmin) "Tienda Retail Sur (Admin)" else "Tienda Retail Sur (Usuario)")
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = navContainerColor,
                            titleContentColor = unselectedColor,
                        ),
                        actions = {
                            TextButton(onClick = { isDarkTheme = !isDarkTheme }) {
                                Text(
                                    text = if (isDarkTheme) "Claro" else "Oscuro",
                                    color = unselectedColor,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            TextButton(onClick = { isAdmin = !isAdmin }) {
                                Text(
                                    text = if (isAdmin) "Usuario" else "Admin",
                                    color = unselectedColor,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Rutas.LOGIN, // ----------------- CAMBIO AL LOGIN COMO INICIO -----------------
                modifier = if (mostrarBarraPruebas) Modifier.padding(innerPadding) else Modifier
            ) {
                composable(Rutas.PRUEBAS) { PantallaPruebas(navController) }

                // ----------------- PANTALLAS CONECTADAS -----------------
                composable(Rutas.LOGIN) {
                    LoginScreen(
                        onLoginSuccess = { usuarioAutenticado ->
                            // Aquí más adelante verificaremos si el usuarioAutenticado es P-01 (Admin) o P-04 (Vendedor)
                            // Por ahora, navegamos al menú principal al ingresar correctamente.
                            navController.navigate(Rutas.MENU_PRINCIPAL)
                        },
                        onOlvideContrasenaClick = {
                            navController.navigate(Rutas.RECUPERAR_CONTRASENA)
                        },
                        onCrearCuentaClick = {
                            navController.navigate(Rutas.FORM_REGISTRO)
                        }
                    )
                }

                composable(Rutas.RECUPERAR_CONTRASENA) {
                    RecuperarContrasenaScreen(
                        onVolverALoginClick = {
                            navController.popBackStack() // Cierra recuperación y vuelve al Login
                        }
                    )
                }

                composable(Rutas.FORM_REGISTRO) {
                    FormularioRegistroScreen(
                        onIrALoginClick = {
                            navController.navigate(Rutas.LOGIN) {
                                popUpTo(Rutas.LOGIN) { inclusive = true } // Evita que se acumulen pantallas
                            }
                        }
                    )
                }
                // --------------------------------------------------------

                composable(Rutas.MENU_PRINCIPAL) { MenuPrincipalScreen(isAdmin = isAdmin) }
                composable(Rutas.FORM_CONTEO) { FormularioConteoScreen() }

                composable(Rutas.ADMIN_DASHBOARD) {
                    AdminDashboardScreen(
                        onGestionUsuarios = { navController.navigate(Rutas.GESTION_USUARIOS) },
                        onAjustes = { navController.navigate(Rutas.AJUSTES) },
                        onCerrarSesionClick = { navController.popBackStack(Rutas.LOGIN, inclusive = false) }
                    )
                }

                // Pantallas que aún no existen
                composable(Rutas.GESTION_USUARIOS) { PantallaPendiente("Gestión de usuarios") }
                composable(Rutas.AJUSTES) { PantallaPendiente("Ajustes") }
            }
        }
    }
}

@Composable
private fun PantallaPruebas(navController: NavHostController) {
    val destinos = listOf(
        "Login" to Rutas.LOGIN,
        "Menú principal" to Rutas.MENU_PRINCIPAL,
        "Formulario de conteo" to Rutas.FORM_CONTEO,
        "Formulario de registro" to Rutas.FORM_REGISTRO,
        "Recuperar contraseña" to Rutas.RECUPERAR_CONTRASENA,
        "Dashboard admin" to Rutas.ADMIN_DASHBOARD,
        "Gestión de usuarios (pendiente)" to Rutas.GESTION_USUARIOS,
        "Ajustes (pendiente)" to Rutas.AJUSTES
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Text("Menú de pruebas", style = MaterialTheme.typography.headlineSmall)
        destinos.forEach { (nombre, ruta) ->
            Button(
                onClick = { navController.navigate(ruta) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(nombre)
            }
        }
    }
}

@Composable
private fun PantallaPendiente(nombre: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("$nombre\n(por desarrollar)", style = MaterialTheme.typography.titleMedium)
    }
}