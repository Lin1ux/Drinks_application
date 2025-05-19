package com.example.drinki

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch


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

//Wyświetla skrollowalną listę drinków
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentList(navController : NavController,viewModel: DrinkViewModel,takeAll : Boolean)
{
    val drinkInfoList = viewModel.drinksState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(takeAll) {
        viewModel.loadDrinks(takeAll)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            NavigationMenu(navController,{ scope.launch { drawerState.close() } },false)
        })
    {
        Scaffold(topBar = {
            AppBar(navController,"Wybierz Drink",starButton(),{ scope.launch { drawerState.open()}})
        })
        { innePadding ->
            LazyColumn(modifier = Modifier.padding(innePadding))
            {
                itemsIndexed(drinkInfoList.value.chunked(2)) { index, pair ->
                    Row {
                        pair.forEach { drink ->
                            Box(modifier = Modifier.weight(1f).padding(16.dp)) {
                                ImageCard(
                                    drink = drink,
                                    navController = navController
                                )
                            }
                        }
                        if (pair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
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
fun TabletContentList(navController : NavController,viewModel: DrinkViewModel,takeAll : Boolean)
{
    val drinkInfoList = viewModel.drinksState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(takeAll) {
        viewModel.loadDrinks(takeAll)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            NavigationMenu(navController,{ scope.launch { drawerState.close() } },true)
        })
    {
        Scaffold(topBar = {
            AppBar(navController,"Wybierz Drink",starButton(),{ scope.launch { drawerState.open()}})
        })
        { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding))
            {
                itemsIndexed(drinkInfoList.value.chunked(4)) { index, pair ->
                    Row {
                        pair.forEach { drink ->
                            Box(modifier = Modifier.weight(1f).padding(16.dp)) {
                                ImageCard(
                                    drink = drink,
                                    navController = navController
                                )
                            }
                        }
                        if (pair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}


//Wyświetlana karty drinku
@Composable
fun ImageCard(
    drink: DrinkInfo,
    modifier: Modifier = Modifier,
    navController : NavController
)
{
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = cardElevation(defaultElevation = 5.dp)  //Cieniowanie (daje efekt jakby element był przed tłem
    )
    {
        Box(
            modifier = Modifier.height(200.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center)
        {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = drink.imageId),
                contentDescription = drink.title,
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
                )
            Box(                                                                        //Kontener służący do nałożenia gradientu
                modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(    //Brush pozwala wypełniać na różne sposoby ( w tym przypadku gradient)
                    colors = listOf(Color.Transparent,Color.Black),                     //Lista kolorów gradientu (przeźroczysty do czarnego)
                    startY = 300f                                                       //Początek pojawiania się gradientu
                ))
            )
            Box(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentAlignment = Alignment.BottomCenter
                )
            {
                Text(drink.title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
            Button(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent),
                onClick =
                    {
                        //Przejście do innej aktywności (informacji o drinku)
                        navController.navigate(Screen.DetailScreen.withArgs(drink.title,drink.description,drink.howToPrepare,drink.time.toString(),drink.imageId.toString(),drink.uid.toString()))   //Przejście do nowej sceny
                    }
            )
            {

            }
        }
    }
}


