package com.example.tiendaretailsur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tiendaretailsur.ui.screens.FormularioRegistroScreen
import com.example.tiendaretailsur.ui.screens.LoginScreen
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiRegistroTheme {
                var mostrarRegistro by remember { mutableStateOf(false) }

                if (mostrarRegistro) {
                    FormularioRegistroScreen()
                } else {
                    LoginScreen(
                        onCrearCuentaClick = { mostrarRegistro = true }
                    )
                }
            }
        }
    }
}   