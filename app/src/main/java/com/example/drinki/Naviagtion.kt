package com.example.drinki

import androidx.compose.foundation.layout.Box
import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drinki.database.DrinkViewModel
import com.example.drinki.ui.theme.DrinkiTheme


//Komponent do obsługi nawigacje po aplikacji
@Composable
fun Navigation(viewModel: DrinkViewModel)
{
    val navController = rememberNavController()
    viewModel.initViewModel()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route)
    {
        //Główny ekran (lista drinków)
        composable(route = Screen.MainScreen.route)
        {
            DrinkiTheme {
                MainScreen(navController = navController,viewModel)
            }
        }
        //Szczegóły drinka
        composable (
            route = Screen.DetailScreen.route + "/{name}/{description}/{preparing}/{duration}/{imageId}",   //ścieżka i argumenty przekazywane
            arguments = listOf(
                navArgument("name")
                {
                    type = NavType.StringType
                    defaultValue = "Drink"
                    nullable = true
                },
                navArgument("duration") {
                    type = NavType.StringType
                    defaultValue = "5"
                },
                navArgument("imageId") {
                    type = NavType.StringType
                    defaultValue = "0"
                }
            )
        )
        {   entry ->
            //Ekran szczegółów z przekazanymi argumentami podanymi z ścieżki
            DrinkiTheme {
                SelectDetailScreen(
                    navController = navController,
                    title = entry.arguments?.getString("name"),
                    description = entry.arguments?.getString("description") ?: "",
                    preparing = entry.arguments?.getString("preparing") ?: "",
                    duration = entry.arguments?.getString("duration") ?: "5",
                    imageId = entry.arguments?.getString("imageId") ?: "0"
                )
            }
        }
    }
}
//Wyświetla odpowiedni ekran listy drinków zależnie od ułożenia urządzenia
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(navController : NavController,viewModel: DrinkViewModel)
{
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)    //Pobranie klasy ekranu

    //Sprawdzenie wielkości ekranu i wyświetlenie odpowiedniej listy
    when (windowSizeClass.widthSizeClass)   //when działa podobnie do switcha
    {
        //Kompaktowy rozmiar
        WindowWidthSizeClass.Compact -> {
            ContentList(navController,viewModel) //Wyświetlanie listy
        }
        //Rozszerzony rozmiar
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            TabletContentList(navController,viewModel)
        }
    }
}

//Wyświetla odpowiedni ekran szczegółów zależnie od ułożenia urządzenia
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SelectDetailScreen(navController : NavController,title: String?,description: String?,preparing: String?,duration: String?,imageId: String?)
{
    val viewModel: TimerViewModel = viewModel()         //View Model timera, który trzyma wartości w przypadku zmiany stanu
    val lifecycleOwner = LocalLifecycleOwner.current    //Lifecycle monitoruje zachowanie aplikacji

    //Dodanie obserwatora co pozwala na dzia
    DisposableEffect(lifecycleOwner) {                          //Obiekt do zarządzania obserwatorami
        lifecycleOwner.lifecycle.addObserver(viewModel)         //Dodanie obserwatora dla view Modela, dzięki temu może wywoływać funkcję (np. @OnLifecycleEvent...) na podstawie zdarzeń
        onDispose {                                             //Wywoływane podczas zamykania aplikacji
            lifecycleOwner.lifecycle.removeObserver(viewModel)  //Usuwa obserwator w celu uniknięcia wycieku pamięci
        }
    }

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)    //Pobranie klasy ekranu

    //Obsługa gestu ruchu palcem w celu cofnięcia się do poprzedniego ekranu
    var back = false
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {                                   //Wykrywa akcje związane z inputem (dotych)
            detectHorizontalDragGestures { _, dragAmount ->     //Wykrywa ruch horzyontalny
                if (dragAmount > 30f && !back)                          //Gest w Prawo
                {
                    back = true
                    navController.popBackStack()
                }
            }
        })
    {
        //Sprawdzenie wielkości ekranu i wyświetlenie odpowiedniego układu
        when (windowSizeClass.widthSizeClass)   //when działa podobnie do switcha
        {
            //Kompaktowy rozmiar
            WindowWidthSizeClass.Compact -> {   // -> są częścia whena
                DetailScreen(
                    viewModel = viewModel,
                    title = title,
                    description = description,
                    preparing = preparing,
                    duration = duration?.toIntOrNull() ?: 5)
            }
            //Rozszerzony rozmiar
            WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
                DetailScreenTablet(
                    viewModel = viewModel,
                    title = title,
                    description = description,
                    preparing = preparing,
                    duration = duration?.toIntOrNull() ?: 5,
                    imageId = imageId?.toIntOrNull() ?: 0)
            }
        }
    }
}

