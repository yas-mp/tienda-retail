package com.example.tiendaretailsur

import MenuPrincipalScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaretailsur.navigation.NavegacionApp
import com.example.tiendaretailsur.ui.screens.FormularioRegistroScreen
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiRegistroTheme {
                FormularioRegistroScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiRegistroTheme() {
       FormularioRegistroScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "1.- Admin - Claro")
@Composable
private fun AppAdminClaroPreview() {
    NavegacionApp(initialDarkTheme = false)
}

@Preview(showBackground = true, showSystemUi = true, name = "2.- Admin - Oscuro")
@Composable
private fun AppAdminOscuroPreview() {
    NavegacionApp(initialDarkTheme = true)
}

@Preview(showBackground = true, showSystemUi = true, name = "3.- Usuario - Claro")
@Composable
private fun ColaboradorClaroPreview() {
    MiRegistroTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "4.- Usuario - Oscuro")
@Composable
private fun ColaboradorOscuroPreview() {
    MiRegistroTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MenuPrincipalScreen(isAdmin = false)
        }
    }
}