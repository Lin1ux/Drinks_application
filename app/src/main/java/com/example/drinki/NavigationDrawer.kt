package com.example.drinki

import android.graphics.Color.alpha
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class,ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController : NavController,
           title: String,
           RightButton : starButton = starButton(),
           onMenuClick: () -> Unit = {})
{

    TopAppBar(
        modifier = Modifier.height(100.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
        title = {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                Text(title, textAlign = TextAlign.Center)
            }
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.fillMaxHeight(),
                onClick = onMenuClick )
            {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu",tint = Color.White)
            }
        },
        actions = {
            var clicked : Boolean = false

            IconButton(
                modifier = Modifier.fillMaxHeight(),
                enabled = RightButton.show,
                onClick = {
                    if (!clicked)
                    {
                        RightButton.OnClick()
                    }
                }
            )
            {
                if(RightButton.show)
                {
                    if(RightButton.fill)
                    {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Star",tint = Color.White)
                    }
                    else
                    {
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = "Star",tint = Color.White.copy(alpha = 0.5f))
                    }

                }
            }
        }
    )
}

@Composable
fun NavigationMenu(
    navController: NavController,
    backAction: () -> Unit = {},
    tablet : Boolean = false
)
{

    Row(modifier = Modifier.fillMaxWidth().clickable{ backAction() })
    {
        Column(modifier = Modifier.weight(0.7f).fillMaxSize())
        {
            Spacer(modifier = Modifier.weight(0.1f))
            Column(modifier = Modifier.weight(if(tablet) 0.4f else 0.2f).clip(RoundedCornerShape(5.dp)).background(MaterialTheme.colorScheme.background))
            {
                Box(modifier = Modifier.fillMaxWidth().weight(0.3f).background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center)
                {
                    Text("Drink App",style = TextStyle(fontSize = 24.sp), textAlign = TextAlign.Center,color = MaterialTheme.colorScheme.onSurface)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable() { navController.navigate(Screen.MainScreen.withArgs("true")) }
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp))
                )
                {
                    Icon(modifier = Modifier.padding(5.dp),imageVector = Icons.Filled.Star, contentDescription = "Gwiazdka",tint = Color.White)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text("Wszystkie Drinki",style = TextStyle(fontSize = 18.sp),modifier = Modifier.padding(5.dp),color = Color.White)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable() { navController.navigate(Screen.MainScreen.withArgs("false")) }
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp))
                )
                {

                    Icon(modifier = Modifier.padding(5.dp),imageVector = Icons.Outlined.Star, contentDescription = "Gwiazdka",tint = Color.White)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text("Ulubione",style = TextStyle(fontSize = 18.sp),modifier = Modifier.padding(5.dp),color = Color.White)
                }

            }
            Spacer(modifier = Modifier.weight(if(tablet) 0.5f else 0.7f).clickable{backAction()})
        }
        Spacer(modifier = Modifier.weight(0.3f))
    }
}

data class starButton(
        var show : Boolean = false,
        var fill : Boolean = false,
        var OnClick: () -> Unit = {}
)