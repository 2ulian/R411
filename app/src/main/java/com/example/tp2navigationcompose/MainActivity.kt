package com.example.tp2navigationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp2navigationcompose.ui.theme.TP2NavigationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP2NavigationComposeTheme {
                MaterialTheme {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // contrôleur navigation
    val navController = rememberNavController()

    // Définir le système de navigation
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(navController = navController)
        }
        composable(route = "form") {
            FormScreen(navController = navController)
        }
        composable(
            route = "display/{name}/{age}",
            arguments = listOf(
                navArgument(name = "name") { defaultValue = "" },
                navArgument(name = "age") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString(key = "name") ?: ""
            val age = backStackEntry.arguments?.getString(key = "age") ?: ""
            DisplayScreen(navController = navController, name = name, age = age)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    TP2NavigationComposeTheme {
        AppNavigation()
    }
}

@Composable
fun HomeScreen(navController: androidx.navigation.NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue dans ma première application compose navigation",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate(route = "form") }) {
            Text(text = "Accéder au formulaire")
        }
    }
}

@Composable
fun FormScreen(navController: androidx.navigation.NavHostController) {
    var name by remember { mutableStateOf(value = "") }
    var age by remember { mutableStateOf(value = "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Page du formulaire.",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = name,
            onValueChange = { newText -> name = newText },
            label = { Text(text = "Entrez votre nom") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = age,
            onValueChange = { newText -> age = newText },
            label = { Text(text = "Entrez votre age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate(route = "display/$name/$age") }) {
            Text(text = "Valider")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Retour")
        }
    }
}

@Composable
fun DisplayScreen(
    navController: androidx.navigation.NavHostController,
    name: String,
    age: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Vous avez $age ans !",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Retour")
        }
    }
}