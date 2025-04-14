package com.example.drinki

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: TimerViewModel,title: String?,description: String?,tablet : Boolean)
{
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.DarkGray,
                titleContentColor = Color.White
            ),
            title = { Text(text = title ?: "Brak danych",style = TextStyle(color = Color.White, fontSize = 48.sp), textAlign = TextAlign.Center)
                /*Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                Text("Wybierz Drink")}*/
            }
        )
    })
    {innerPadding ->
        Column(modifier = Modifier.padding(innerPadding))
        {
            //Napis z nazwą drinka
            /*Box(

                modifier =
                    Modifier.fillMaxWidth().background(Color.LightGray),
                contentAlignment = Alignment.Center // Wyśrodkowanie tekstu
            ) {
                // Dodanie Text wewnątrz Box
                Text(text = title ?: "Brak danych",style = TextStyle(color = Color.White, fontSize = 48.sp))
            }*/
            //Skrolowane rzeczy
            LazyColumn()
            {
                //Składniki drinka
                item()
                {
                    // Dodanie Text wewnątrz Box
                    Column(modifier = Modifier.padding(15.dp))
                    {
                        Box(
                            modifier = Modifier.padding(15.dp).fillMaxWidth(),
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
                //timer
                item()
                {
                    Timer(viewModel  = viewModel,tablet = false)
                }
            }
        }
    }
}

@Composable
fun Timer(viewModel: TimerViewModel,tablet : Boolean)
{
    // Pobieranie wartości bezpośrednio z ViewModel
    val time = viewModel.time
    val isRunning = viewModel.isRunning

    //Odliczanie czasu w osobnym wątku
    LaunchedEffect(isRunning)
    {
        while (isRunning)
        {
            delay(1000L)
            viewModel.updateTime()
        }
    }
    Column()
    {
        Row(modifier = Modifier.fillMaxWidth())
        {
            if (!tablet)
            {
                Spacer(modifier = Modifier.weight(0.3f))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3250A8)),
                    onClick = {
                        viewModel.switchTimer()
                    }
                )
                {
                    Text(if(isRunning) {"Stop"} else {"Start"}, style = TextStyle(color = Color.White, fontSize = 32.sp))

                }
                Spacer(modifier = Modifier.weight(0.3f))
            }
            else
            {
                Spacer(modifier = Modifier.weight(0.5f))
                Button(
                    modifier = Modifier.weight(0.5f),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3250A8)),
                    onClick = {
                        viewModel.switchTimer()
                    }
                )
                {
                    Text(
                        if(isRunning) {"Stop"} else {"Start"},                    //Można dawać ifa do argumentu
                        style = TextStyle(color = Color.White, fontSize = 32.sp))
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }

        }
        Box(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            contentAlignment = Alignment.Center
        )
        {
            Text("$time", style = TextStyle(color = Color.Black, fontSize = 64.sp))
        }
    }
}

//View Model, który trzyma wartości między stanami
class TimerViewModel : ViewModel(), LifecycleObserver
{
    //mutableStateOf zapisuje wartości zmiennych podczas zmiany stanu
    var duration by mutableStateOf(5)           //Czas trwania timera
    var time by mutableStateOf(5)               //Pozostały czas
    var isRunning by mutableStateOf(false)      //Czy timer działa

    var canCount = false

    //Zatrzymanie timera kiedy użytkownik minimalizuje aplikajce
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) //Wywołanie kiedy użytkownik ma zminimalizowaną aplikację
    fun unactive()
    {
        canCount = false
    }

    //Włączenie timera kiedy użytkownik widzi aplikacje
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) //Wywołanie kiedy użytkownik ma wyświetloną aplikacje
    fun active()
    {
        canCount = true
    }

    //Zmiana stanu timera
    fun switchTimer()
    {
        isRunning = !isRunning
    }
    //Resetowanie wartości timera
    fun resetTimer()
    {
        time = duration
        isRunning = false
    }
    //Aktualizacja timera
    fun updateTime()
    {
        //Jeśli aplikacja nie jest wyświetlana nie pozwala liczyć
        if(!canCount)
        {
            return
        }
        //zmniejszenie wartości licznika
        if (isRunning && time > 0)
        {
            time--
        }
        else if (time <= 0)
        {
            resetTimer()
        }
    }
}
