package com.example.drinki.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drink(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0, // unikalne ID (auto-generowane)
    val name: String,           //drink name
    val ingredients: String,    //drink ingredient
    val description: String,    //drink description
    val prepareDuration: Int,   //for timer
    val imageId: Int
)
