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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.model.SeccionAjustesAdmin
import com.example.tiendaretailsur.ui.screens.AdminDashboardScreen
import com.example.tiendaretailsur.ui.screens.AjustesAdminScreen
import com.example.tiendaretailsur.ui.screens.FormularioConteoScreen
import com.example.tiendaretailsur.ui.screens.FormularioRegistroScreen
import com.example.tiendaretailsur.ui.screens.LoginScreen
import com.example.tiendaretailsur.ui.screens.MenuPrincipalScreen
import com.example.tiendaretailsur.ui.screens.PerfilScreen
import com.example.tiendaretailsur.ui.screens.RecuperarContrasenaScreen
import com.example.tiendaretailsur.ui.theme.CardOscura
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.ui.theme.TextoOscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionApp(initialDarkTheme: Boolean = isSystemInDarkTheme()) {
    var isAdmin by rememberSaveable { mutableStateOf(true) }
    var isDarkTheme by rememberSaveable { mutableStateOf(initialDarkTheme) }

    val context = LocalContext.current
    val dbHelper = remember { UsuarioDbHelper(context) }

    val navController = rememberNavController()
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    // Ocultar la barra superior de pruebas en pantallas principales de navegación limpia
    val mostrarBarraPruebas = rutaActual !in listOf(
        Ruta.AdminPanel.ruta,
        Ruta.AjustesAdmin.ruta,
        Ruta.Perfil.ruta
    )

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
                startDestination = Ruta.Login.ruta,
                modifier = if (mostrarBarraPruebas) Modifier.padding(innerPadding) else Modifier
            ) {
                composable(Ruta.Login.ruta) {
                    LoginScreen(
                        dbHelper = dbHelper, // <--- Agrega esta línea
                        onLoginSuccess = { usuarioAutenticado ->
                            isAdmin = usuarioAutenticado.perfil in listOf("P-01", "P-02", "SUBGERENTE")
                            navController.navigate(Ruta.MenuPrincipal.ruta) {
                                popUpTo(Ruta.Login.ruta) { inclusive = true }
                            }
                        },
                        onOlvideContrasenaClick = {
                            navController.navigate(Ruta.RecuperacionContrasena.ruta)
                        },
                        onCrearCuentaClick = {
                            navController.navigate(Ruta.FormularioRegistro.ruta)
                        }
                    )
                }

                composable(Ruta.RecuperacionContrasena.ruta) {
                    RecuperarContrasenaScreen(
                        dbHelper = dbHelper,
                        onVolverALoginClick = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Ruta.FormularioRegistro.ruta) {
                    FormularioRegistroScreen(
                        dbHelper = dbHelper,
                        onIrALoginClick = {
                            navController.navigate(Ruta.Login.ruta) {
                                popUpTo(Ruta.Login.ruta) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Ruta.MenuPrincipal.ruta) {
                    MenuPrincipalScreen(
                        isAdmin = isAdmin,
                        onIrAdminDashboard = {
                            navController.navigate(Ruta.AdminPanel.ruta)
                        },
                        onIrPerfil = {
                            navController.navigate(Ruta.Perfil.ruta)
                        },
                        onCerrarSesion = {
                            navController.navigate(Ruta.Login.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Ruta.Perfil.ruta) {
                    PerfilScreen(
                        isAdmin = isAdmin,
                        onVolver = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Ruta.FormularioConteo.ruta) {
                    FormularioConteoScreen()
                }

                composable(Ruta.AdminPanel.ruta) {
                    AdminDashboardScreen(
                        dbHelper = dbHelper,
                        onGestionUsuarios = { navController.navigate(Ruta.AjustesAdmin.ruta) },
                        onAjustes = { navController.navigate(Ruta.AjustesAdmin.ruta) },
                        onCerrarSesionClick = {
                            navController.navigate(Ruta.Login.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Ruta.AjustesAdmin.ruta) {
                    AjustesAdminScreen(
                        dbHelper = dbHelper,
                        seccionInicial = SeccionAjustesAdmin.COLABORADORES,
                        onVolverClick = { navController.popBackStack() },
                        onIrAMiPerfilClick = { navController.navigate(Ruta.Perfil.ruta) }
                    )
                }
            }
        }
    }
}