package com.example.drinki

import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drinki.ui.theme.DrinkiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //MainScreen()
            Navigation()
        }
    }
}



//Zwraca listę wyświetlanych drinków
/*@Composable
fun DrinkList() : List<Drink>
{
    val image1 = painterResource(id = R.drawable.bluelagoon)
    val image2 = painterResource(id = R.drawable.cubalibre)
    val image3 = painterResource(id = R.drawable.eldiablo)
    val image4 = painterResource(id = R.drawable.espressomartini)
    val image5 = painterResource(id = R.drawable.godfather)
    val image6 = painterResource(id = R.drawable.kamikaze)
    val image7 = painterResource(id = R.drawable.mojito)
    val image8 = painterResource(id = R.drawable.pinacolada)
    val image9 = painterResource(id = R.drawable.sexonbeach)

    return listOf(
        Drink(image1, "", "Blue Lagoon"),
        Drink(image2, "", "Cuba Libre"),
        Drink(image3, "", "El Diablo"),
        Drink(image4, "", "Espresso Martini"),
        Drink(image5, "", "GodFather"),
        Drink(image6, "", "Kamikaze"),
        Drink(image7, "", "Mojito"),
        Drink(image8, "", "Pina Colada"),
        Drink(image9, "", "Sex On Beach"),
    )
}
*/
//Wyświetla skrollowalną listę
@Composable
fun ContentList(drinks : List<Drink>, navController : NavController)
{
    LazyColumn()    //Układ kolumnowy, który pozwala na przewijanie jeśli się nie zmieszczą na ekranie
    {
        for(i in drinks.indices step 2) //Iterowanie co 2 indeksy
        {
            item()  //Element leniwej kolumny
            {
                Row()   //Rząd aby uporządkować zawartość
                {
                    Box(modifier = Modifier.weight(1f).padding(16.dp)) //Kontener do przechowania przycisków
                    {
                        ImageCard(painter = drinks[i].image, contentDescriptor = drinks[i].description, title = drinks[i].title,navController = navController)
                    }
                    if (i+1<drinks.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))
                        {
                            ImageCard(painter = drinks[i+1].image, contentDescriptor = drinks[i+1].description, title = drinks[i+1].title,navController = navController)
                        }
                    }
                    else
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))  //Wstawienie pustego pudła aby nie zaburzyć układu
                        {
                        }
                    }
                }
            }
        }
    }
}

//Wyświetlanie głównego ekranu na tabletach
@Composable
fun TabletContentList(drinks : List<Drink>, navController : NavController)
{
    LazyColumn()    //Układ kolumnowy, który pozwala na przewijanie jeśli się nie zmieszczą na ekranie
    {
        for(i in drinks.indices step 4) //Iterowanie co 4 indeksy
        {
            item()  //Element leniwej kolumny
            {
                Row()   //Rząd aby uporządkować zawartość
                {
                    Box(modifier = Modifier.weight(1f).padding(16.dp)) //Kontener do przechowania przycisków
                    {
                        ImageCard(painter = drinks[i].image, contentDescriptor = drinks[i].description, title = drinks[i].title,navController = navController)
                    }
                    if (i+1<drinks.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))
                        {
                            ImageCard(painter = drinks[i+1].image, contentDescriptor = drinks[i+1].description, title = drinks[i+1].title,navController = navController)
                        }
                    }
                    else
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))  //Wstawienie pustego pudła aby nie zaburzyć układu
                        {
                        }
                    }
                    if (i+2<drinks.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))
                        {
                            ImageCard(painter = drinks[i+2].image, contentDescriptor = drinks[i+2].description, title = drinks[i+2].title,navController = navController)
                        }
                    }
                    else
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))  //Wstawienie pustego pudła aby nie zaburzyć układu
                        {
                        }
                    }
                    if (i+3<drinks.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))
                        {
                            ImageCard(painter = drinks[i+3].image, contentDescriptor = drinks[i+3].description, title = drinks[i+3].title,navController = navController)
                        }
                    }
                    else
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp))  //Wstawienie pustego pudła aby nie zaburzyć układu
                        {
                        }
                    }
                }
            }
        }
    }
}


//Wyświetlana karta drinku
@Composable
fun ImageCard(
    painter: Painter,
    contentDescriptor: String,
    title: String,
    modifier: Modifier = Modifier,
    navController : NavController
)
{
    Card(
        modifier = modifier.fillMaxWidth(),                 //Rozciągnięniecie na maksymalną dostępną szerogokść
        shape = RoundedCornerShape(15.dp),                  //Zaokrąglenie rogów
        elevation = cardElevation(defaultElevation = 5.dp)  //Cieniowanie (daje efekt jakby element był przed tłem
    )
    {
        Box(
            modifier = Modifier.height(200.dp).fillMaxWidth().background(Color.Yellow),  //Zapewnienie pełnej szerokości
            contentAlignment = Alignment.Center)                                        //Wyśrodkowanie
        {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,                          //Obraz
                contentDescription = contentDescriptor,     //Opis
                contentScale = ContentScale.FillBounds,     //Wypełnienie kontenera
                alignment = Alignment.Center                //Wyśrodkowanie obrazu
                )
            Box(                                                                        //Kontener służący do nałożenia gradientu
                modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(    //Brush pozwala wypełniać na różne sposoby ( w tym przypadku gradient)
                    colors = listOf(Color.Transparent,Color.Black),                     //Lista kolorów gradientu (przeźroczysty do czarnego)
                    startY = 300f                                                       //Początek pojawiania się gradientu
                ))
            )
            Box(                                                                        //Kontener trzymający napis
                modifier = Modifier.fillMaxSize().padding(12.dp),                       //Maksymalne wypełnienie i margines od wewnątrz (padding tak jak w css)
                contentAlignment = Alignment.BottomCenter                               //Wyśrodkowanie na dole
                )
            {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))   //Text (czcionkę podaje się w "sp")
            }
            Button(
                modifier = Modifier.fillMaxSize(),                                                  // Wypełnienie całej dostępnej przestrzeni
                shape = RoundedCornerShape(15.dp),                                                  // Zaokrąglenie przycisku na rogach (tak jak box
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent),           // Kolor tła przycisku
                onClick =                                                                           // Kod, który wykona się po naciśnięciu przycisku
                    {
                        navController.navigate(Screen.DetailScreen.withArgs(title,contentDescriptor))   //Przejście do nowej sceny
                    }
            )
            {

            }
        }
    }
}
