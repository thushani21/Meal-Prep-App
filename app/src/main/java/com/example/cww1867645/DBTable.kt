package com.example.cww1867645
//https://developer.android.com/reference/kotlin/android/widget/ScrollView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DBTable : AppCompatActivity() {

    lateinit var dbTitle: TextView
    lateinit var tableData: TextView
    private val dbData: ArrayList<String> = ArrayList()
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_table)
        dbTitle = findViewById(R.id.tableTitle)
        tableData = findViewById(R.id.tableData)

        // Loading saved preferences
        if (savedInstanceState != null) {
            val tableTitle = savedInstanceState.getString("dbTitle")
            val dbValues = savedInstanceState.getString("tableValues")

            dbTitle.text = tableTitle
            tableData.text = dbValues
        }

        // Displaying the meal list in the Database
        displayMealList()
    }

    /** Saving the state of the view widgets and its corresponding values when orientation changes. **/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("dbTitle", dbTitle.text.toString())
        outState.putString("tableValues", tableData.text.toString())
    }

    private fun displayMealList(){
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDatabase").fallbackToDestructiveMigration().build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                val meals: List<Meal> = mealDao.getAll()
                for (m in meals) {
                    dbData.add("\n ${m.name}\n ${m.drinkAlternate}\n ${m.category}\n ${m.area}\n ${m.instructions}\n ${m.mealthumb}\n ${m.tags}\n ${m.youtube}\n ${m.ingredient}\n ${m.measure}\n ${m.source}\n" +
                            " ${m.imgsource}\n" +
                            " ${m.creativecommonsconfirmed}\n" +
                            " ${m.datemodified}\n")
                }

                for (m in dbData) {
                    result += m + "\n"
                }
                tableData.setText(result)
            }
        }
    }
}
