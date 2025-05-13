package com.example.drinki

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.drinki.database.DrinkViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: DrinkViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return DrinkViewModel(application) as T
                    }
                }
            )
            Navigation(viewModel)
        }
    }
}

//Wyświetla skrollowalną listę
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentList(drinks : List<DrinkInfo>, navController : NavController,viewModel: DrinkViewModel)
{
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.height(75.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            title = { Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                Text("Wybierz Drink")}
            }
        )
    })
    { innePadding ->
        LazyColumn(modifier = Modifier.padding(innePadding))    //Układ kolumnowy, który pozwala na przewijanie jeśli się nie zmieszczą na ekranie
        {
            for(i in viewModel.drinkInfoList.indices step 2) //Iterowanie co 2 indeksy
            {
                item()  //Element leniwej kolumny
                {
                    Row()   //Rząd aby uporządkować zawartość
                    {
                        Box(modifier = Modifier.weight(1f).padding(16.dp)) //Kontener do przechowania przycisków
                        {
                            ImageCard(
                                drink = viewModel.drinkInfoList[i],
                                navController = navController)
                        }
                        if (i+1<viewModel.drinkInfoList.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                        {
                            Box(modifier = Modifier.weight(1f).padding(16.dp))
                            {
                                ImageCard(
                                    drink = viewModel.drinkInfoList[i + 1],
                                    navController = navController)
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
}

//Wyświetlanie głównego ekranu na tabletach
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletContentList(drinks : List<DrinkInfo>, navController : NavController,viewModel: DrinkViewModel)
{
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.height(75.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            title = { Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                Text("Wybierz Drink")}
            }
        )
    })
    { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding))    //Układ kolumnowy, który pozwala na przewijanie jeśli się nie zmieszczą na ekranie
        {
            for (i in viewModel.drinkInfoList.indices step 4) //Iterowanie co 4 indeksy
            {
                item()  //Element leniwej kolumny
                {
                    Row()   //Rząd aby uporządkować zawartość
                    {
                        Box(
                            modifier = Modifier.weight(1f).padding(16.dp)
                        ) //Kontener do przechowania przycisków
                        {
                            ImageCard(
                                drink = viewModel.drinkInfoList[i],
                                navController = navController
                            )
                        }
                        if (i + 1 < viewModel.drinkInfoList.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                        {
                            Box(modifier = Modifier.weight(1f).padding(16.dp))
                            {
                                ImageCard(
                                    drink = viewModel.drinkInfoList[i + 1],
                                    navController = navController
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier.weight(1f).padding(16.dp)
                            )  //Wstawienie pustego pudła aby nie zaburzyć układu
                            {
                            }
                        }
                        if (i + 2 < viewModel.drinkInfoList.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                        {
                            Box(modifier = Modifier.weight(1f).padding(16.dp))
                            {
                                ImageCard(
                                    drink = viewModel.drinkInfoList[i + 2],
                                    navController = navController
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier.weight(1f).padding(16.dp)
                            )  //Wstawienie pustego pudła aby nie zaburzyć układu
                            {
                            }
                        }
                        if (i + 3 < viewModel.drinkInfoList.size)    //Jeśli liczba elementów jest nie parzysta nie załaduje obrazu (bo nie ma z czego)
                        {
                            Box(modifier = Modifier.weight(1f).padding(16.dp))
                            {
                                ImageCard(
                                    drink = viewModel.drinkInfoList[i + 3],
                                    navController = navController
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier.weight(1f).padding(16.dp)
                            )  //Wstawienie pustego pudła aby nie zaburzyć układu
                            {
                            }
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
    /*painter: Painter,
    contentDescriptor: String,
    preparing: String,
    title: String,
    imageId : Int = 0,*/
    drink: DrinkInfo,
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
                painter = painterResource(id = drink.imageId),                          //Obraz
                contentDescription = drink.title,     //Opis
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
                Text(drink.title, style = TextStyle(color = Color.White, fontSize = 16.sp))   //Text (czcionkę podaje się w "sp")
            }
            Button(
                modifier = Modifier.fillMaxSize(),                                                  // Wypełnienie całej dostępnej przestrzeni
                shape = RoundedCornerShape(15.dp),                                                  // Zaokrąglenie przycisku na rogach (tak jak box
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent),           // Kolor tła przycisku
                onClick =                                                                           // Kod, który wykona się po naciśnięciu przycisku
                    {
                        navController.navigate(Screen.DetailScreen.withArgs(drink.title,drink.description,drink.howToPrepare,drink.time.toString(),drink.imageId.toString()))   //Przejście do nowej sceny
                    }
            )
            {

            }
        }
    }
}


