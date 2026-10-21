package com.example.tiendaretailsur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaretailsur.navigation.NavegacionApp
import com.example.tiendaretailsur.ui.screens.AdminDashboardScreen
import com.example.tiendaretailsur.ui.screens.LoginScreen
import com.example.tiendaretailsur.ui.screens.MenuPrincipalScreen
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

@Preview(showBackground = true, showSystemUi = true, name = "6.- Usuario - Claro")
@Composable
private fun ColaboradorClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "7.- Usuario - Oscuro")
@Composable
private fun ColaboradorOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "8.- Dashboard - Claro")
@Composable
private fun AdminDashBoardClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AdminDashboardScreen()
        }
    }
}