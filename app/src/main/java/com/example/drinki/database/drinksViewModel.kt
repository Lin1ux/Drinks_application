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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.collections.map

//View Model do trzymania wartości pobranych z bazy danych
class DrinkViewModel(
    application: Application) : AndroidViewModel(application)
{
    private val dao = AppDatabase.getInstance(application).drinkDao()
    var drinks = MutableStateFlow<List<Drink>>(emptyList())
    val _drinkList = MutableStateFlow<List<DrinkInfo>>(emptyList())

    var drinkInfoList by mutableStateOf<List<DrinkInfo>>(emptyList())
    //State Flow
    private val _drinksState = MutableStateFlow<List<DrinkInfo>>(emptyList())
    val drinksState: StateFlow<List<DrinkInfo>> = _drinksState.asStateFlow()

    fun isFavoriteByUid(uid: Int): Flow<Boolean> {
        return dao.getDrink(uid).map { drink ->
            drink?.isFavorite ?: false
        }
    }

    fun switchFavorite(uid: Int) {
        viewModelScope.launch {
            val drink = dao.getDrink(uid).first()
            drink?.let {
                dao.updateDrink(it.copy(isFavorite = !it.isFavorite))
            }

            _drinkList.update { currentList ->
                currentList.map { drink ->
                    if (drink.uid == uid) drink.copy(isFavorite = !drink.isFavorite)
                    else drink
                }
            }
        }
    }

    fun loadDrinks(allDrinks: Boolean) {
        viewModelScope.launch {
            val drinksFlow = if (allDrinks) dao.getAllDrinks() else dao.getFavoriteDrinks()
            drinksFlow.collect { drinksList ->
                _drinksState.value = drinksList.map { drink ->
                    DrinkInfo(
                        uid = drink.uid,
                        imageId = drink.imageId,
                        description = drink.ingredients,
                        howToPrepare = drink.description,
                        title = drink.name,
                        time = drink.prepareDuration,
                        isFavorite = drink.isFavorite
                    )
                }
            }
        }
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
                            imageId = drink.imageId,
                            isFavorite = false
                        )
                    )
                }
            }
            //Pobranie danych z bazy
            loadDrinks(true)
        }
    }
}