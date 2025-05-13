package com.example.drinki

//klasa do zarządzania ścieżkami i aktywnościami
sealed class Screen(val route: String)
{
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")

    //Pozwala na przekazywanie wielu argumentów
    fun withArgs(vararg args: String): String
    {
        return buildString()
        {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}