package com.example.drinki.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//Encja drinków
@Entity
data class Drink(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0, //unikalne ID (auto-generowane)
    val name: String,           //nazwa
    val ingredients: String,    //składniki
    val description: String,    //sposób przyrządzenia
    val prepareDuration: Int,   //czas trwania czasomierza
    val imageId: Int,           //id obrazu
    val isFavorite: Boolean     //czy jest ulubione
)