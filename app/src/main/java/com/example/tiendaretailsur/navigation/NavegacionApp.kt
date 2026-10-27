package com.example.tiendaretailsur.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme

@Composable
fun NavegacionApp(initialDarkTheme: Boolean = isSystemInDarkTheme()) {
    var isAdmin by rememberSaveable { mutableStateOf(true) }
    var isDarkTheme by rememberSaveable { mutableStateOf(initialDarkTheme) }
    var correoUsuarioLogueado by rememberSaveable { mutableStateOf("admin@rincondelsur.cl") }

    val context = LocalContext.current
    val dbHelper = remember { UsuarioDbHelper(context) }
    val navController = rememberNavController()

    MiRegistroTheme(darkTheme = isDarkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Ruta.Login.ruta,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Ruta.Login.ruta) {
                    LoginScreen(
                        dbHelper = dbHelper,
                        onLoginSuccess = { usuarioAutenticado ->
                            correoUsuarioLogueado = usuarioAutenticado.correo
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
                            navController.navigate(Ruta.AjustesAdmin.ruta) {
                                popUpTo(Ruta.AjustesAdmin.ruta) { inclusive = true }
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
                        correoLogueado = correoUsuarioLogueado,
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
                        onVolverInicio = { navController.navigate(Ruta.MenuPrincipal.ruta) },
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
                        onIrAMiPerfilClick = { navController.navigate(Ruta.Perfil.ruta) },
                        onIrARegistroColaborador = { navController.navigate(Ruta.FormularioRegistro.ruta)}
                    )
                }
            }
        }
    }
}