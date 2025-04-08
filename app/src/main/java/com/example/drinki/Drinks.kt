package com.example.drinki

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

//Klasa do trzymania informacji o drinkach
data class Drink(
    var image : Painter,
    var description : String,
    var title : String
)

@Composable
fun getDrinkList(): List<Drink>
{
    val text1 : String =    "• 50 ml wódka,\n" +
                            "• 20 ml Blue Curacao,\n" +
                            "• 100 ml Sprite,\n" +
                            "• kostki lodu,\n" +
                            "• plasterek cytryny lub limonki do dekoracji."

    val text2 : String =    "• 50 ml rumu\n" +
                            "• 100 ml Coli\n" +
                            "• 2 ćwiartki limonki"

    val text3 : String =    "• 45 ml tequili.\n" +
                            "• 15 ml likier z czarnych porzeczek\n" +
                            "• 15 ml świeżo wyciśniętego soku z limonki.\n" +
                            "• 90 ml piwa imbirowego.\n" +
                            "• Plasterek limonki do dekoracji."

    val text4 : String =    "• 40 ml wódki,\n" +
                            "• 25 ml gorącego espresso,\n" +
                            "• 20 ml likieru kawowego,\n" +
                            "• kostki lodu,\n" +
                            "• ziarna kawy do dekoracji."

    val text5 : String =    "• 50 ml whisky.\n" +
                            "• 25 ml amaretto.\n" +
                            "• kostki lodu.\n" +
                            "• plasterek cytryny lub pomarańczy do dekoracji."

    val text6 : String =    "• 30 ml wódki\n" +
                            "• 30 ml Blue Curacao\n" +
                            "• 15 ml soku z cytryny\n" +
                            "• Plasterek cytryny lub limonki do dekoracji\n" +
                            "• Kostki lodu\n"

    val text7 : String =    "• 50 ml białego rumu;\n" +
                            "• 2 łyżeczki cukru trzcinowego;\n" +
                            "• Kilka listków mięty;\n" +
                            "• 25 ml wody gazowanej;\n" +
                            "• Kruszony lód.\n"
                            "• Pół limonki + ćwiartka do dekoracji;\n"

    val text8 : String =    "• 120 ml soku ananasowego\n" +
                            "• 60 ml rumu kokosowego np. Malibu\n" +
                            "• 30 ml śmietanki lub gęstego mleczka kokosowego\n" +
                            "• 1 łyżeczka świeżo wyciśniętego soku z limonki\n" +
                            "• garść kostek lodu"

    val text9 : String =    "• 40 ml wódki - klasyczna lub smakowa (np. morelowa)\n" +
                            "• 20 ml likieru brzoskwiniowego\n" +
                            "• 40 ml soku pomarańczowego\n" +
                            "• 40 ml soku z żurawiny 100%\n" +
                            "• dwie garście kostek lodu"



    return listOf(
        Drink(painterResource(id = R.drawable.bluelagoon), text1, "Blue Lagoon"),
        Drink(painterResource(id = R.drawable.cubalibre), text2, "Cuba Libre"),
        Drink(painterResource(id = R.drawable.eldiablo), text3, "El Diablo"),
        Drink(painterResource(id = R.drawable.espressomartini), text4, "Espresso Martini"),
        Drink(painterResource(id = R.drawable.godfather), text5, "GodFather"),
        Drink(painterResource(id = R.drawable.kamikaze), text6, "Kamikaze"),
        Drink(painterResource(id = R.drawable.mojito), text7, "Mojito"),
        Drink(painterResource(id = R.drawable.pinacolada), text8, "Pina Colada"),
        Drink(painterResource(id = R.drawable.sexonbeach), text9, "Sex On Beach"),
    )
}