package com.example.drinki

//Klasa do trzymania informacji o drinkach
data class DrinkInfo(
    var imageId : Int = R.drawable.bluelagoon,
    var description : String,
    var howToPrepare : String,
    var title : String,
    var time : Int
)

//Zwraca listę drinków
fun getDrinkList(): List<DrinkInfo>
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
                            "• Kruszony lód.\n" +
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
        DrinkInfo(R.drawable.bluelagoon, text1,"Wstrząsnąć w shakerze z lodem", "Blue Lagoon",15),
        DrinkInfo(R.drawable.cubalibre, text2,"Delikatnie zamieszać w szklance", "Cuba Libre",10),
        DrinkInfo(R.drawable.eldiablo, text3,"Zamieszać w szklance z lodem", "El Diablo",10),
        DrinkInfo(R.drawable.espressomartini, text4,"Mocno wstrząsnąć w shakerze z lodem", "Espresso Martini",20),
        DrinkInfo(R.drawable.godfather, text5,"Zamieszać w szklance", "GodFather",10),
        DrinkInfo(R.drawable.kamikaze, text6, "Wstrząsnąć w shakerze z lodem","Kamikaze",15),
        DrinkInfo(R.drawable.mojito, text7,"Delikatnie zamieszać łyżką barmańską w szklance", "Mojito",10),
        DrinkInfo(R.drawable.pinacolada, text8,"Wstrząsnąć w shakerze z lodem", "Pina Colada",20),
        DrinkInfo(R.drawable.sexonbeach, text9,"Zamieszać delikatnie w szklance", "Sex On Beach",10),
    )

}