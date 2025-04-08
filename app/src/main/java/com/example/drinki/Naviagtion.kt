package com.example.drinki

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.lifecycleScope

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
            SelectDetailScreen(
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SelectDetailScreen(title: String?,description: String?)
{
    val viewModel: TimerViewModel = viewModel()         //View Model timera, który trzyma wartości w przypadku zmiany stanu
    val lifecycleOwner = LocalLifecycleOwner.current    //Lifecycle monitoruje zachowanie aplikacji

    DisposableEffect(lifecycleOwner) {                          //Obiekt do zarządzania obserwatorami
        lifecycleOwner.lifecycle.addObserver(viewModel)         //Dodanie obserwatora dla view Modela, dzięki temu może wywoływać funkcję (np. @OnLifecycleEvent...) na podstawie zdarzeń
        onDispose {                                             //Wywoływane podczas zamykania aplikacji
            lifecycleOwner.lifecycle.removeObserver(viewModel)  //Usuwa obserwator w celu uniknięcia wycieku pamięci
        }
    }

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)    //Pobranie klasy ekranu
    //
    when (windowSizeClass.widthSizeClass)   //when działa podobnie do switcha
    {
        //Kompaktowy rozmiar
        WindowWidthSizeClass.Compact -> {   // -> są częścia whena
            DetailScreen(
                viewModel = viewModel,
                title = title,
                description = description,
                tablet = false)
        }
        //Rozszerzony rozmiar
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            DetailScreen(
                viewModel = viewModel,
                title = title,
                description = description,
                tablet = true)
        }
    }
}

