package com.example.drinki

import android.app.Application
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.lifecycleScope
import com.example.drinki.database.AppDatabase
import com.example.drinki.database.DrinkViewModel
import com.example.drinki.ui.theme.DrinkiTheme


@Composable
fun Navigation(viewModel: DrinkViewModel)
{
    val navController = rememberNavController()
    //val drinks = viewModel.drinks.collectAsState()
    viewModel.initViewModel()



    //val drinks by viewModel.drinks.collectAsState()
    //drinkViewModel.initViewModel()

    //val drinks by viewModel.users.observeAsState(emptyList())
    NavHost(navController = navController, startDestination = Screen.MainScreen.route)
    {
        composable(route = Screen.MainScreen.route)
        {
            DrinkiTheme {
                MainScreen(navController = navController,viewModel)
            }
        }
        composable (
            route = Screen.DetailScreen.route + "/{name}/{description}/{preparing}/{duration}/{imageId}",
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(navController : NavController,viewModel: DrinkViewModel)
{
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)    //Pobranie klasy ekranu
    //
    when (windowSizeClass.widthSizeClass)   //when działa podobnie do switcha
    {
        //Kompaktowy rozmiar
        WindowWidthSizeClass.Compact -> {
            ContentList(getDrinkList(),navController,viewModel) //Wyświetlanie listy
        }
        //Rozszerzony rozmiar
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            TabletContentList(getDrinkList(),navController,viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SelectDetailScreen(navController : NavController,title: String?,description: String?,preparing: String?,duration: String?,imageId: String?)
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

