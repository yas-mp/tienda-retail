package com.example.tiendaretailsur.navigation

import MenuPrincipalScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import com.example.tiendaretailsur.ui.theme.CardOscura
import com.example.tiendaretailsur.ui.theme.MiRegistroTheme
import com.example.tiendaretailsur.ui.theme.TextoOscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionApp(initialDarkTheme: Boolean = isSystemInDarkTheme()) {
    // Estados para rol
    var isAdmin by rememberSaveable { mutableStateOf(true) }
    var isDarkTheme by rememberSaveable { mutableStateOf(initialDarkTheme) }

    val navContainerColor = CardOscura
    val unselectedColor = TextoOscuro

    MiRegistroTheme(darkTheme = isDarkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(if (isAdmin) "Tienda Retail Sur (Admin)" else "Tienda Retail Sur (usuario)")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = navContainerColor,
                        titleContentColor = unselectedColor,
                    ),
                    actions = {
                        // Boton para alternar modo oscuro y claro
                        TextButton(onClick = { isDarkTheme = !isDarkTheme }) {
                            Text(
                                text = if (isDarkTheme) "Claro" else "Oscuro",
                                color = unselectedColor,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        // Boton para alternar entre Admin y Usuario
                        TextButton(onClick = { isAdmin = !isAdmin }) {
                            Text(
                                text = if (isAdmin) "usuario" else "Admin",
                                color = unselectedColor,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)


            MenuPrincipalScreen(isAdmin = isAdmin, modifier = modifier)
        }
    }
}