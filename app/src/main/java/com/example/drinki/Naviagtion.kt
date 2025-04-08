package com.example.drinki

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Navigation()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route)
    {
        composable(route = Screen.MainScreen.route)
        {
            MainScreen(navController = navController)
        }
        composable (
            route = Screen.DetailScreen.route + "/{name}/{description}",
            arguments = listOf(
                navArgument("name")
                {
                    type = NavType.StringType
                    defaultValue = "Drink"
                    nullable = true
                }
            )
        )
        {   entry ->
            DetailScreen(
                title = entry.arguments?.getString("name"),
                description = entry.arguments?.getString("description"))
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(navController : NavController)
{
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)    //Pobranie klasy ekranu
    //
    when (windowSizeClass.widthSizeClass)   //when działa podobnie do switcha
    {
        //Kompaktowy rozmiar
        WindowWidthSizeClass.Compact -> {
            ContentList(getDrinkList(),navController) //Wyświetlanie listy
        }
        //Rozszerzony rozmiar
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            TabletContentList(getDrinkList(),navController)
        }
    }

}

@Composable
fun DetailScreen(title: String?,description: String?)
{

    /*Column(modifier = Modifier.fillMaxSize()) {
        //Wyświetlanie tytułu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center // Wyśrodkowanie tekstu
        ) {
            // Dodanie Text wewnątrz Box
            Text(text = title ?: "Brak danych",style = TextStyle(color = Color.White, fontSize = 48.sp))
        }

        //Wyświetlanie opisu
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(15.dp),
            contentAlignment = Alignment.TopCenter // Wyśrodkowanie tekstu w górnej części
        ) {
            // Dodanie Text wewnątrz Box
            Column()
            {
                Box(
                    modifier = Modifier.height(75.dp).fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter)
                {
                    Text(text = "Składniki",style = TextStyle(color = Color.Black, fontSize = 32.sp))
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart)
                {
                    Text(text = description ?: "Brak danych",style = TextStyle(color = Color.Black, fontSize = 20.sp))
                }


            }

        }
        //
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(15.dp).background(Color.LightGray),
            contentAlignment = Alignment.TopCenter // Wyśrodkowanie tekstu w górnej części
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.weight(0.3f))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3250A8)),
                    onClick = {

                    }
                )
                {
                    Text("Start", style = TextStyle(color = Color.White, fontSize = 32.sp))
                }
                Spacer(modifier = Modifier.weight(0.3f))

            }

                //Text("0", style = TextStyle(color = Color.Black, fontSize = 32.sp))

        }
        /*Box(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(15.dp).background(Color.LightGray),
            contentAlignment = Alignment.Center // Wyśrodkowanie tekstu w górnej części
            )
        {

        }*/
    }*/
}