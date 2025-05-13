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
    fun getAllDrinks(): Flow<List<Drink>> // get all drinks
    @Insert
    suspend fun insertDrink(drink: Drink) //add new drink
    @Update
    suspend fun updateDrink(drink: Drink) //update drink
    @Delete
    suspend fun deleteDrink(drink: Drink) //delete drink
    @Query("DELETE FROM drink")
    suspend fun deleteAll()
}