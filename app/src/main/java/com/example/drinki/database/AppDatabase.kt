package com.example.drinki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Drink::class], version = 3)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun drinkDao(): DrinkDao
    companion object
    {
        //Tworzenie instancji bazy danych
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "drinks_db"
                ).addMigrations(MIGRATION_2_3 /* , MIGRATION_2_3, … , itd.*/)
                .build().also { INSTANCE = it }
            }
    }
}

//Przykładowa migracja

private val MIGRATION_2_3 = object : Migration(2, 3)
{
    override fun migrate(database: SupportSQLiteDatabase)
    {
        database.execSQL("ALTER TABLE drink ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
    }
}