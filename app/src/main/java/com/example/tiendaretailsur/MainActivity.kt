package com.example.tiendaretailsur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaretailsur.data.UsuarioDbHelper
import com.example.tiendaretailsur.navigation.NavegacionApp
import com.example.tiendaretailsur.ui.screens.AdminDashboardScreen
import com.example.tiendaretailsur.ui.screens.LoginScreen
import com.example.tiendaretailsur.ui.screens.MenuPrincipalScreen
import com.example.tiendaretailsur.ui.screens.PerfilScreen
import com.example.tiendaretailsur.ui.screens.RecuperarContrasenaScreen
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacionApp()
        }
    }
}

// =========================================================================
// PREVIEWS
// =========================================================================

@Preview(showBackground = true, showSystemUi = true, name = "1.- Login - Claro")
@Composable
private fun LoginClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        LoginScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "2.- Login - Oscuro")
@Composable
private fun LoginOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        LoginScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "3.- Recuperar Contraseña - Claro")
@Composable
private fun RecuperarClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        RecuperarContrasenaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "4.- Admin - Claro")
@Composable
private fun AppAdminClaroPreview() {
    NavegacionApp(initialDarkTheme = false)
}

@Preview(showBackground = true, showSystemUi = true, name = "5.- Admin - Oscuro")
@Composable
private fun AppAdminOscuroPreview() {
    NavegacionApp(initialDarkTheme = true)
}

@Preview(showBackground = true, showSystemUi = true, name = "6.- Menu Pricnipal Usuario - Claro")
@Composable
private fun ColaboradorClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "7.- Menu Pricnipal Usuario - Oscuro")
@Composable
private fun ColaboradorOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}
@Preview(showBackground = true, showSystemUi = true, name = "8.- Menu Principal Admin - Claro")
@Composable
private fun MenuPrincipalAdminClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = true)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "9.- Menu Principal Admin - Oscuro")
@Composable
private fun MenuPrincipalAdminOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = true)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "10.- Dashboard - Claro")
@Composable
private fun AdminDashBoardClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AdminDashboardScreen(dbHelper = UsuarioDbHelper(LocalContext.current))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "11.- Perfil - Claro")
@Composable
private fun PerfilClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "12.- Perfil - Oscuro")
@Composable
private fun PerfilOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = false)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "13.- Perfil Admin - Claro")
@Composable
private fun PerfilAdminClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = true)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "14.- Perfil Admin - Oscuro")
@Composable
private fun PerfilAdminOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PerfilScreen(isAdmin = true)
        }
    }
}