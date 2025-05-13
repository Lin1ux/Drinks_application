package com.example.drinki

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: TimerViewModel,title: String?,description: String?,preparing: String?,duration: Int = 5)
{
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            title = {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center)
                {
                    Text(
                        text = title ?: "Brak danych",
                        style = TextStyle(color = Color.White, fontSize = 48.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        )

    },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = {
                    showDialog = true

                },
                containerColor = MaterialTheme.colorScheme.primary
            )
            {
                Icon(
                    modifier = Modifier.height(30.dp),
                    imageVector = Icons.Default.Message,
                    contentDescription = "SMS",
                    tint = Color.White
                )
            }
        }
    )
    {innerPadding ->
        Column(modifier = Modifier.padding(innerPadding))
        {
            PhoneNumberDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false},
                onAccept = { number ->
                    showDialog = false
                    sendSms(number, description?: "", context)
                }
            )
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
                            Text(text = "Składniki",style = TextStyle(fontSize = 32.sp))
                        }
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopStart)
                        {
                            Text(text = description ?: "Brak danych",style = TextStyle(fontSize = 20.sp))
                        }
                        Box(
                            modifier = Modifier.padding(15.dp).fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter)
                        {
                            Text(text = "Przygotowanie",style = TextStyle(fontSize = 32.sp))
                        }
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center)
                        {
                            Text(text = preparing ?: "Brak danych",style = TextStyle(fontSize = 20.sp))
                        }


                    }
                }
                //timer
                item()
                {
                    Timer(viewModel  = viewModel,duration,tablet = false)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTablet(viewModel: TimerViewModel,title: String?,description: String?,preparing: String?,duration: Int = 5,imageId: Int = 0)
{
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            title = {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center)
                {
                    Text(
                        text = title ?: "Brak danych",
                        style = TextStyle(color = Color.White, fontSize = 48.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        )

    },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = {
                    sendSms("607726641", description?: "", context)
                },
                containerColor = MaterialTheme.colorScheme.primary
            )
            {
                Icon(
                    modifier = Modifier.height(30.dp),
                    imageVector = Icons.Default.Message,
                    contentDescription = "SMS",
                    tint = Color.White
                )
            }
        }
    )
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
                    Row()
                    {
                        // Dodanie Text wewnątrz Box
                        Column(modifier = Modifier.weight(0.6f).padding(15.dp))
                        {
                            Box(
                                modifier = Modifier.padding(15.dp).fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter)
                            {
                                Text(text = "Składniki",style = TextStyle(fontSize = 32.sp))
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopStart)
                            {
                                Text(text = description ?: "Brak danych",style = TextStyle(fontSize = 20.sp))
                            }
                            Box(
                                modifier = Modifier.padding(15.dp).fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter)
                            {
                                Text(text = "Przygotowanie",style = TextStyle(fontSize = 32.sp))
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center)
                            {
                                Text(text = preparing ?: "Brak danych",style = TextStyle(fontSize = 20.sp))
                            }
                        }
                        Box(modifier = Modifier.weight(0.4f).padding(15.dp))
                        {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = imageId),                          //Obraz
                                contentDescription = "image",     //Opis
                                contentScale = ContentScale.Crop ,          //Wypełnienie kontenera
                                alignment = Alignment.Center                //Wyśrodkowanie obrazu
                            )
                        }
                    }
                }
                //timer
                item()
                {
                    Timer(viewModel  = viewModel,duration,tablet = false)
                }
            }
        }
    }
}

//Komponent czasomierza
@Composable
fun Timer(viewModel: TimerViewModel,duration : Int,tablet : Boolean)
{
    // Pobieranie wartości bezpośrednio z ViewModel
    viewModel.setNewDuration(duration)
    //val time = viewModel.time
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
                Spacer(modifier = Modifier.weight(0.25f))
                Button(
                    modifier = Modifier.weight(0.25f).padding(5.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(),
                    onClick = {
                        viewModel.switchTimer()
                    }
                )
                {
                    //Text(if(isRunning) {"Stop"} else {"Start"}, style = TextStyle(color = Color.White, fontSize = 32.sp))
                    if(isRunning)
                    {
                        Icon(
                            modifier = Modifier.height(30.dp),
                            imageVector = Icons.Default.Pause,
                            contentDescription = "Stop",
                            tint = Color.White
                        )
                    }
                    else
                    {
                        Icon(
                            modifier = Modifier.height(30.dp),
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start",
                            tint = Color.White
                        )
                    }
                }
                Button(
                    modifier = Modifier.weight(0.25f).padding(5.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(),
                    onClick = {
                        viewModel.stopTimer()
                        viewModel.resetTimer()
                    }
                )
                {
                    Icon(
                        modifier = Modifier.height(30.dp),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Restart",
                        tint = Color.White

                    )
                }
                Spacer(modifier = Modifier.weight(0.25f))
            }
            else
            {
                Spacer(modifier = Modifier.weight(0.25f))
                Button(
                    modifier = Modifier.weight(0.25f),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(),
                    onClick = {
                        viewModel.switchTimer()
                    }
                )
                {
                    Text(
                        if(isRunning) {"Stop"} else {"Start"},                    //Można dawać ifa do argumentu
                        style = TextStyle(color = Color.White, fontSize = 32.sp))
                }
                Button(
                    modifier = Modifier.weight(0.25f),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(),
                    onClick = {
                        viewModel.switchTimer()
                    }
                )
                {
                    Text("Restart", style = TextStyle(color = Color.White, fontSize = 32.sp))
                }
                Spacer(modifier = Modifier.weight(0.25f))
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            contentAlignment = Alignment.Center
        )
        {
            Text("${viewModel.time}", style = TextStyle(fontSize = 64.sp))
        }
    }
}

//View Model, który trzyma wartości między stanami
class TimerViewModel : ViewModel(), LifecycleObserver
{
    //mutableStateOf zapisuje wartości zmiennych podczas zmiany stanu
    var duration by mutableIntStateOf(5)            //Czas trwania timera
    var time by mutableIntStateOf(5)                //Pozostały czas
    var isRunning by mutableStateOf(false)          //Czy timer działa
    var canSetTime : Boolean = true                 //Czy time może zostać zmieniony

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
    fun setNewDuration(value : Int)
    {
        duration = value
        if(canSetTime)
        {
            time = duration
            canSetTime = false
        }
    }
    //Zmiana stanu timera
    fun switchTimer()
    {
        isRunning = !isRunning
    }
    //Zatrzymanie timer
    fun stopTimer()
    {
        isRunning = false
    }

    //Resetowanie wartości timera
    fun resetTimer()
    {
        time = duration
        isRunning = false
        canSetTime = true
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

//Ekran do wprowadzania numeru telefonu
@Composable
fun PhoneNumberDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAccept: (String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }

    if (showDialog)
    {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Podaj numer telefonu") },
            text = {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Numer telefonu") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAccept(phoneNumber)
                        onDismiss()
                    },
                    enabled = phoneNumber.isNotBlank(),
                ) {
                    Text("OK",color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )) {
                    Text("Anuluj",color = Color.White)
                }
            }
        )
    }
}
