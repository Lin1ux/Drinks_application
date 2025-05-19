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
    @Query("SELECT * FROM drink d WHERE uid = :uid LIMIT 1")
    fun getDrink(uid : Int): Flow<Drink?> //pobranie drinka o podanym id z bazy
    @Query("UPDATE drink SET isFavorite = :isFavorite WHERE uid = :uid")
    suspend fun updateFavorite(uid: Int, isFavorite: Boolean)
    @Query("SELECT * FROM drink WHERE isFavorite = 1")
    fun getFavoriteDrinks(): Flow<List<Drink>> //pobranie wszystkich drinków z bazy
    @Insert
    suspend fun insertDrink(drink: Drink) //dodanie nowego drinku
    @Update
    suspend fun updateDrink(drink: Drink) //aktualizacja drinku
    @Delete
    suspend fun deleteDrink(drink: Drink) //usunięcie drinku
    @Query("DELETE FROM drink")
    suspend fun deleteAll()               //wyczyszczenie bazy danych
}