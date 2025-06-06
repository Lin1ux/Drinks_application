package com.example.drinki

import android.util.Log
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
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.Input
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import com.example.drinki.database.DrinkViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController,viewModel: TimerViewModel,drinkViewModel: DrinkViewModel,title: String?,description: String?,preparing: String?,uid : Int?,duration: Int = 5)
{
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            NavigationMenu(navController,{ scope.launch { drawerState.close() } },false)
        })
    {
        val isFavorite by drinkViewModel.isFavoriteByUid(uid ?: 0).collectAsState(initial = false)

        Scaffold(
            topBar = {
                AppBar( navController = navController,
                    title =  title?:"",
                    RightButton = starButton(
                        true,
                        isFavorite,
                        {   Log.d("Akcja",isFavorite.toString())
                            drinkViewModel.switchFavorite(uid?: 0)
                        }
                    ),
                    onMenuClick =  { scope.launch { drawerState.open()}})
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
        { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding))
            {
                PhoneNumberDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    onAccept = { number ->
                        showDialog = false
                        sendSms(number, description ?: "", context)
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
                                contentAlignment = Alignment.TopCenter
                            )
                            {
                                Text(text = "Składniki", style = TextStyle(fontSize = 32.sp))
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopStart
                            )
                            {
                                Text(
                                    text = description ?: "Brak danych",
                                    style = TextStyle(fontSize = 20.sp)
                                )
                            }
                            Box(
                                modifier = Modifier.padding(15.dp).fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter
                            )
                            {
                                Text(text = "Przygotowanie", style = TextStyle(fontSize = 32.sp))
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Text(
                                    text = preparing ?: "Brak danych",
                                    style = TextStyle(fontSize = 20.sp)
                                )
                            }


                        }
                    }
                    //timer
                    item()
                    {
                        Timer(viewModel = viewModel, duration)
                    }
                    //Animacja
                    item()
                    {
                        TimerAnimation(viewModel.isRunning)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTablet(navController: NavController,viewModel: TimerViewModel,drinkViewModel: DrinkViewModel,title: String?,description: String?,preparing: String?,uid : Int?,duration: Int = 5,imageId: Int = 0)
{
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            NavigationMenu(navController,{ scope.launch { drawerState.close() } },true)
        })
    {
        Scaffold(
            topBar = {
                AppBar( navController = navController,
                        title =  title?:"",
                        RightButton = starButton(
                            true,
                            false,
                            {}
                        ),
                        onMenuClick =  { scope.launch { drawerState.open()}})
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
        { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding))
            {
                PhoneNumberDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    onAccept = { number ->
                        showDialog = false
                        sendSms(number, description ?: "", context)
                    }
                )
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
                                    contentAlignment = Alignment.TopCenter
                                )
                                {
                                    Text(text = "Składniki", style = TextStyle(fontSize = 32.sp))
                                }
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.TopStart
                                )
                                {
                                    Text(
                                        text = description ?: "Brak danych",
                                        style = TextStyle(fontSize = 20.sp)
                                    )
                                }
                                Box(
                                    modifier = Modifier.padding(15.dp).fillMaxWidth(),
                                    contentAlignment = Alignment.TopCenter
                                )
                                {
                                    Text(
                                        text = "Przygotowanie",
                                        style = TextStyle(fontSize = 32.sp)
                                    )
                                }
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                )
                                {
                                    Text(
                                        text = preparing ?: "Brak danych",
                                        style = TextStyle(fontSize = 20.sp)
                                    )
                                }
                            }
                            Box(modifier = Modifier.weight(0.4f).padding(15.dp))
                            {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = imageId),                          //Obraz
                                    contentDescription = "image",               //Opis
                                    contentScale = ContentScale.Crop,           //Wypełnienie kontenera
                                    alignment = Alignment.Center                //Wyśrodkowanie obrazu
                                )
                            }
                        }
                    }
                    //timer
                    item()
                    {
                        Timer(viewModel = viewModel, duration)
                    }
                    //Animacja
                    item()
                    {
                        TimerAnimation(viewModel.isRunning)
                    }
                }
            }
        }
    }
}

//Komponent czasomierza
@Composable
fun Timer(viewModel: TimerViewModel,duration : Int)
{
    // Pobieranie wartości bezpośrednio z ViewModel
    viewModel.setNewDuration(duration)
    //val time = viewModel.time
    val isRunning = viewModel.isRunning
    var showDialog by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.weight(0.2f))
            Button(
                modifier = Modifier.weight(0.2f).padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(),
                onClick = {
                    viewModel.switchTimer()
                }
            )
            {
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
                modifier = Modifier.weight(0.2f).padding(5.dp),
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
            Button(
                modifier = Modifier.weight(0.2f).padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(),
                onClick = {
                    showDialog = true
                }
            )
            {
                Icon(
                    modifier = Modifier.height(30.dp),
                    imageVector = Icons.Filled.Input,
                    contentDescription = "Wprowadzenie danych",
                    tint = Color.White

                )
            }
            Spacer(modifier = Modifier.weight(0.2f))
        }
        Box(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            contentAlignment = Alignment.Center
        )
        {
            Text("${viewModel.time}", style = TextStyle(fontSize = 64.sp))
        }
    }
    TimerDialog(
                startValue = viewModel.duration,
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onAccept = { value ->
                viewModel.ResetAndSetDuration(value)
        })
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
//Ekran do zmieniania wartości timera
@Composable
fun TimerDialog(
    startValue: Int,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAccept: (Int) -> Unit
) {
    var time by remember { mutableStateOf(startValue.toString()) }

    if (showDialog)
    {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Ustaw czas") },
            text = {
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Czas (s)") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAccept(time.toIntOrNull()?: startValue)
                        onDismiss()
                    },
                    enabled = true,
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
