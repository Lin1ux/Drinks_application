package com.example.drinki.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao
{
    @Query("SELECT * FROM drink")
    fun getAllDrinks(): Flow<List<Drink>> //pobranie wszystkich drinków z bazy
    @Insert
    suspend fun insertDrink(drink: Drink) //dodanie nowego drinku
    @Update
    suspend fun updateDrink(drink: Drink) //aktualizacja drinku
    @Delete
    suspend fun deleteDrink(drink: Drink) //usunięcie drinku
    @Query("DELETE FROM drink")
    suspend fun deleteAll()               //wyczyszczenie bazy danych
}