package com.example.cww1867645

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}