package com.example.cww1867645
//https://stackoverflow.com/questions/67488655/how-to-display-an-image-from-api-and-send-the-user-to-video-link-after-clicking
//https://www.youtube.com/watch?v=eJEUb4djDgU
//https://github.com/square/picasso

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class Search_meals : AppCompatActivity() {

    private var searchBox: EditText? = null
    private var searchBtn: Button? = null
    private var resultsBox: TextView? = null
    private var mealTitle: String = ""
    private var writtenData: String = ""
    private var image: ImageView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meals)

        // Inflate the views from XML
        searchBox = findViewById(R.id.searchText)
        searchBtn = findViewById(R.id.searchBtn)
        resultsBox = findViewById(R.id.data2)
        image = findViewById(R.id.imageView2)

        // OnClick Listeners
        searchBtn?.setOnClickListener {
            getMeal()
        }
        if (savedInstanceState != null) {
            val landBtn = savedInstanceState.getBoolean("SaveBtn")
            val landSearch = savedInstanceState.getCharSequence("MySavedData")
            val landView = savedInstanceState.getString("viewMeals")
            val imageView2 = savedInstanceState.getBoolean("Image")

            searchBtn?.isEnabled = landBtn
            searchBox?.setText(landSearch)
            resultsBox?.text = landView
            image?.isEnabled = imageView2
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

        if (image?.isEnabled == true) {
            outState.putBoolean("Image", true)
        } else {
            outState.putBoolean("NoImage", false)
        }
    }

    /** Searching the meal name in the database table values; Retrieving the meal's associated recipe name **/
    private fun getMeal() {
        // Access the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDatabase").fallbackToDestructiveMigration().build()
        val mealDao = db.mealDao()

        // Getting the actor name
        val meal = searchBox!!.text.toString().trim()
        if (meal  == "")
            return

        runBlocking {
            launch{
                // Emptying the results box for every search
                mealTitle = ""

                val meals =  mealDao.getSpecificMealTitle(meal)

                mealTitle = "|-------- Recipes-------|\n\n"

                var mealThumb = ""
                for ( x in meals){
                    val mealName = x.name
                    val mealDrinkAlt = x.drinkAlternate
                    val mealCategory = x.category
                    val mealArea = x.area
                    val mealInstruction = x.instructions
                    mealThumb = x.mealthumb.toString()
                    val mealTag = x.tags
                    val mealYT = x.youtube
                    val mealIngredient = x.ingredient
                    val mealMeasure = x.measure
                    val mealSource = x.source
                    val mealImgSource = x.imgsource
                    val mealCreativeCommonsConfirmed = x.creativecommonsconfirmed
                    val mealDateModified = x.datemodified
                    val finalResult =  "\n" + mealName + "\n" + mealDrinkAlt + "\n" + mealCategory + "\n" + mealArea + "\n" + mealInstruction + "\n" + mealThumb + "\n" + mealTag + "\n" + mealYT + "\n" + mealIngredient + "\n" + mealMeasure + "\n" + mealSource + "\n" + mealImgSource + "\n" + mealCreativeCommonsConfirmed + "\n" + mealDateModified + "\n"
                    mealTitle += finalResult
                }

                resultsBox?.text = mealTitle
                val imagesArrayList = mealThumb.split("thumb: ")
                Picasso.get().load(imagesArrayList[1]).into(image)
            }
        }
    }
}
