package com.example.drinki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Drink::class], version = 2)
abstract class AppDatabase : RoomDatabase()
{

    /*private val MIGRATION_1_2 = object : Migration(1, 2)
    {
        override fun migrate(database: SupportSQLiteDatabase)
        {
            database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
        }
    }*/


    abstract fun drinkDao(): DrinkDao
    companion object
    {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "drinks_db"
                ).build().also { INSTANCE = it }
                //.addMigrations(MIGRATION_1_2 /* , MIGRATION_2_3, … , itd.*/)
                //.build().also { INSTANCE = it }
            }
    }
}