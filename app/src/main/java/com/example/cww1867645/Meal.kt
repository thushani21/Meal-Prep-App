package com.example.cww1867645

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal (
    @PrimaryKey

    @ColumnInfo(name = "MealName")
    val name: String,

    @ColumnInfo(name = "DrinkAlternate")
    val drinkAlternate: String?,

    @ColumnInfo(name = "Category")
    val category: String?,

    @ColumnInfo(name = "Area")
    val area: String?,

    @ColumnInfo(name = "Instructions")
    val instructions: String?,

    @ColumnInfo(name = "MealThumb")
    val mealthumb: String?,

    @ColumnInfo(name = "Tags")
    val tags: String?,

    @ColumnInfo(name = "YouTube")
    val youtube: String?,

    @ColumnInfo(name = "Ingredient01")
    val ingredient: String?,

    @ColumnInfo(name = "Measure01")
    val measure: String?,

    @ColumnInfo(name = "Source")
    val source: String?,

    @ColumnInfo(name = "ImageSource")
    val imgsource: String?,

    @ColumnInfo(name = "CreativeCommonsConfirmed")
    val creativecommonsconfirmed: String?,

    @ColumnInfo(name = " dateModified")
    val  datemodified: String?,

)