package com.example.cww1867645

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class All_meals : AppCompatActivity() {
    private var searchBox: EditText? = null
    private var searchBtn: Button? = null
    private var resultsBox: TextView? = null
    private var urlString: String? = null
    private var dbData: String? = null
    private var mealTitles: ArrayList<String> = ArrayList()
    private var writtenData: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_meals)

        // Inflate the views from XML
        searchBox = findViewById(R.id.searchText)
        searchBtn = findViewById(R.id.searchBtn)
        resultsBox = findViewById(R.id.data2)


        // OnClick Listeners
        searchBtn?.setOnClickListener {
            getMeal()
        }
        // Loading and setting up the states of the view widgets and values which are saved in the shared preference
        if (savedInstanceState != null) {
            val landBtn = savedInstanceState.getBoolean("SaveBtn")
            val landSearch = savedInstanceState.getCharSequence("MySavedData")
            val landView = savedInstanceState.getString("viewMeals")

            searchBtn?.isEnabled = landBtn
            searchBox?.setText(landSearch)
            resultsBox?.text = landView
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (searchBtn?.isEnabled == true) {
            outState.putBoolean("SaveBtn", true)
        } else {
            outState.putBoolean("SaveBtn", false)
        }

        writtenData = searchBox?.text.toString()
        outState.putCharSequence("MySavedData", writtenData)

        outState.putString("viewMeals", resultsBox?.text.toString())
    }

    /** Fetching the movie details using the web service; storing the data into a String buffer object; Printing the values after returning the proper data from parseJson function **/
    private fun getMeal() {
        // !! - converts any value to a non-null type and throws an exception if the value is null
        // trim() - removes whitespace from both ends of a string
        val meal = searchBox!!.text.toString().lowercase().trim()
        if (meal == "")
            return

        // Setting up the fetching URL for the web service
        urlString = "https://www.themealdb.com/api/json/v1/1/search.php?s=$meal"

        // Start fetching data in the background
        runBlocking {
            withContext(Dispatchers.IO) {
                // Emptying the results for every btn click
                dbData = ""

                // whole JSON data
                val stb = StringBuilder("")
                val url = URL(urlString)
                val con = url.openConnection() as HttpURLConnection
                val bf: BufferedReader

                try {
                    bf = BufferedReader(InputStreamReader(con.inputStream))
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@withContext
                }

                var line = bf.readLine()
                while (line != null) {
                    stb.append(line)
                    line = bf.readLine()
                }

                dbData = "MEALS RELATED TO THE TERM: \n\n"

                // Picking up all the data
                for (x in parseJSON(stb)) {
                    dbData += "- " + x + "\n"
                }
            }
            resultsBox?.setText(dbData)
        }
    }

    /** Reading the Json data and retrieving the necessary information **/
    private fun parseJSON(stb: StringBuilder): ArrayList<String> {
        // Emptying results
        mealTitles.clear()
        // Extracting the actual data from the JSON data
        val json = JSONObject(stb.toString())

        try {
            val sArray = json.getJSONArray("meals")

            for (i in 0 until sArray.length()) {
                val meals = sArray.getJSONObject(i)
                val mealTitle = meals["strMeal"] as String
                mealTitles.add(mealTitle)
            }
            val allResults = json["totalResults"] as String
            val NoOfResults = "\nTotal retrieved results: $allResults"
            mealTitles.add(NoOfResults)

        } catch (e: JSONException) {
            mealTitles.add("No results found!!!")
        }
        return mealTitles
    }
}