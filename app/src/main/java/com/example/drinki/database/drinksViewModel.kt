package com.example.drinki.database

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinki.R
import com.example.drinki.getDrinkList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.example.drinki.DrinkInfo
import kotlinx.coroutines.flow.first

//View Model do trzymania wartości pobranych z bazy danych
class DrinkViewModel(
    application: Application) : AndroidViewModel(application)
{
    private val dao = AppDatabase.getInstance(application).drinkDao()
    var drinks = MutableStateFlow<List<Drink>>(emptyList())
    var drinkList = MutableStateFlow<List<DrinkInfo>>(emptyList())
    var drinkInfoList by mutableStateOf<List<DrinkInfo>>(emptyList())
        private set

    //na podstawie pobranych danych z bazy danych tworzy listę drinków
    fun getDrinkInfoList()
    {
        val transformedList = mutableListOf<DrinkInfo>()

        //Konwersja Drink z bazy danych do DrinkInfo
        drinks.value.forEach { drink ->
            transformedList.add(
                DrinkInfo(
                    imageId = R.drawable.bluelagoon, // Możesz zmienić na odpowiednią logikę odczytu z obrazów
                    description = drink.ingredients,
                    howToPrepare = drink.description, // Zakładając, że to są składniki
                    title = drink.name,
                    time = drink.prepareDuration
                )
            )
        }

        drinkList.value = transformedList
    }

    //Pobranie wartości z bazy danych jeśli baza danych jest pusta zapełnia ją wartościami domyślnymi
    fun initViewModel()
    {
        drinkInfoList = emptyList()

        viewModelScope.launch {
            val initialDrinks = dao.getAllDrinks().first()  //Pobranie raz danych
            if (initialDrinks.isEmpty())                    //Sprawdzenie czy jest puste
            {
                //dodanie danych jeśli baza jest pusta
                getDrinkList().forEach { drink ->
                    dao.insertDrink(
                        Drink(
                            name = drink.title,
                            ingredients = drink.description,
                            description = drink.howToPrepare,
                            prepareDuration = drink.time,
                            imageId = drink.imageId
                        )
                    )
                }
            }
            //Pobranie danych z bazy
            dao.getAllDrinks().collect { drinksList ->
                drinks.value = drinksList
                drinkInfoList = drinksList.map { drink ->
                    DrinkInfo(
                        imageId = drink.imageId,
                        description = drink.ingredients,
                        howToPrepare = drink.description,
                        title = drink.name,
                        time = drink.prepareDuration
                    )
                }
            }
        }
    }
}