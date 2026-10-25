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
    const val PERFIL = "perfil"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionApp(initialDarkTheme: Boolean = isSystemInDarkTheme()) {
    var isAdmin by rememberSaveable { mutableStateOf(true) }
    var isDarkTheme by rememberSaveable { mutableStateOf(initialDarkTheme) }

    val context = LocalContext.current
    val dbHelper = remember { UsuarioDbHelper(context) }

    val navController = rememberNavController()
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    val mostrarBarraPruebas = rutaActual !in listOf(
        Rutas.ADMIN_DASHBOARD,
        Rutas.AJUSTES,
        Rutas.GESTION_USUARIOS,
        Rutas.PERFIL
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
                startDestination = Rutas.LOGIN,
                modifier = if (mostrarBarraPruebas) Modifier.padding(innerPadding) else Modifier
            ) {
                composable(Rutas.PRUEBAS) { PantallaPruebas(navController) }

                composable(Rutas.LOGIN) {
                    LoginScreen(
                        onLoginSuccess = { usuarioAutenticado ->
                            isAdmin = usuarioAutenticado.perfil in listOf("P-01", "P-02", "SUBGERENTE")
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
                            navController.popBackStack()
                        }
                    )
                }

                composable(Rutas.FORM_REGISTRO) {
                    FormularioRegistroScreen(
                        dbHelper = dbHelper,
                        onIrALoginClick = {
                            navController.navigate(Rutas.LOGIN) {
                                popUpTo(Rutas.LOGIN) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Rutas.MENU_PRINCIPAL) {
                    MenuPrincipalScreen(
                        isAdmin = isAdmin,
                        onIrAdminDashboard = {
                            navController.navigate(Rutas.ADMIN_DASHBOARD)
                        }
                    )
                }

                composable(Rutas.FORM_CONTEO) { FormularioConteoScreen() }

                composable(Rutas.ADMIN_DASHBOARD) {
                    AdminDashboardScreen(
                        dbHelper = dbHelper,
                        onGestionUsuarios = { navController.navigate(Rutas.GESTION_USUARIOS) },
                        onAjustes = { navController.navigate(Rutas.AJUSTES) },
                        onCerrarSesionClick = {
                            navController.popBackStack(Rutas.LOGIN, inclusive = false)
                        }
                    )
                }

                composable(Rutas.GESTION_USUARIOS) {
                    AjustesAdminScreen(
                        seccionInicial = SeccionAjustesAdmin.COLABORADORES,
                        onVolverClick = { navController.popBackStack() },
                        onIrAMiPerfilClick = { navController.navigate(Rutas.PERFIL) }
                    )
                }

                composable(Rutas.AJUSTES) {
                    AjustesAdminScreen(
                        seccionInicial = SeccionAjustesAdmin.PERFILES,
                        onVolverClick = { navController.popBackStack() },
                        onIrAMiPerfilClick = { navController.navigate(Rutas.PERFIL) }
                    )
                }

                composable(Rutas.PERFIL) {
                    PerfilScreen(
                        isAdmin = isAdmin,
                        onVolver = { navController.popBackStack() }
                    )
                }
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
        "Gestión de usuarios" to Rutas.GESTION_USUARIOS,
        "Ajustes de administrador" to Rutas.AJUSTES,
        "Mi Perfil (Foto/Cámara)" to Rutas.PERFIL
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