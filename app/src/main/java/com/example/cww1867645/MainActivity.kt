package com.example.cww1867645
//https://stonesoupprogramming.com/2017/11/17/kotlin-string-formatting/
//https://stackoverflow.com/questions/12492064/android-add-image-to-button

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var saveMealButton: Button
    private lateinit var searchbyingredientButton: Button
    private lateinit var searchMealButton: Button
    private lateinit var allMealButton: Button
    private lateinit var imageLogo: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inflate the views from XML
        saveMealButton = findViewById(R.id.button)
        searchbyingredientButton = findViewById(R.id.button2)
        searchMealButton = findViewById(R.id.button3)
        allMealButton = findViewById(R.id.button4)
        imageLogo = findViewById(R.id.imageView)

        // OnClick Listeners
        saveMealButton.setOnClickListener{
            saveMeals()
        }

        searchbyingredientButton.setOnClickListener{
            displaySearchPage()
        }
//
        searchMealButton.setOnClickListener {
            displayMealSearchPage()
        }
//
        allMealButton.setOnClickListener {
            displayAllMealPage()
        }

        // Loading the stored states of the view widgets
        if (savedInstanceState != null) {
            val logo2 = savedInstanceState.getBoolean("ImageLogo")
            val button1 = savedInstanceState.getBoolean("Btn1IsEnabled")
            val button2 = savedInstanceState.getBoolean("Btn2IsEnabled")
            val button3 = savedInstanceState.getBoolean("Btn3IsEnabled")

            //Assigning values
            imageLogo.isEnabled = logo2
            saveMealButton.isEnabled = button1
            searchbyingredientButton.isEnabled = button2
            searchMealButton.isEnabled = button3
        }
    }

    /** Saving the state of the view widgets and its corresponding values when orientation changes **/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (saveMealButton.isEnabled) {
            outState.putBoolean("Btn1IsEnabled", true)
        } else {
            outState.putBoolean("Btn1IsEnabled", false)
        }
        if (searchbyingredientButton.isEnabled) {
            outState.putBoolean("Btn2IsEnabled", true)
        } else {
            outState.putBoolean("Btn2IsEnabled", false)
        }
        if (searchMealButton.isEnabled) {
            outState.putBoolean("Btn3IsEnabled", true)
        } else {
            outState.putBoolean("Btn3IsEnabled", false)
        }
        if (imageLogo.isEnabled) {
            outState.putBoolean("ImageLogo", true)
        } else {
            outState.putBoolean("NoImageLogo", false)
        }
    }


    /** Setting up the meal objects; Reading all the meal and inserting them to create the database table **/

    private fun saveMeals(){
        // Creating an instance of the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDatabase").build()
        val mealDao = db.mealDao()

        val text = "Meals Added to Database"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)

        runBlocking {
            launch {
                val meal1 = Meal(
                    "Name: " + "Sweet and Sour Pork \n",
                    "Drink Alternative: "+"null\n",
                    "Category: "+"Pork\n",
                    "Area: "+"Chinese\n",
                    "Instructions: "+"Preparation\r\n1. Crack the egg into a bowl. Separate the egg white and yolk." +
                            "\r\n\r\n2. " +
                            "Slice the pork tenderloin into ips.\r\n\r\n3. " +
                            "Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\r\n\r\n4." +
                            " Marinade the pork ips for about 20 minutes.\r\n\r\n5. " +
                            "Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\r\n\r\n" +
                            "Sweet and Sour Pork Cooking Instructions\r\n1. Pour the cooking oil into a wok and heat to 190\u00b0C (375\u00b0F)." +
                            " Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\r\n\r\n2. " +
                            "Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\r\n\r\n3. " +
                            "Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\r\n\r\n4. " +
                            "Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\r\n\r\n5. " +
                            "Serve on a plate and add some coriander for decoration.\n",
                    "Meal thumb: " + "https://www.themealdb.com/images/media/meals/1529442316.jpg \n",
                    "Tags: " + "Sweet \n",
                    "Youtube: " + "https://www.youtube.com/watch?v=mdaBIhgEAMo \n",
                    "Ingredients: " + "Pork,Egg,Water,Salt,Sugar,Soy Sauce,Starch,Tomato Puree,Vinegar,Coriander \n",
                    "Measures: " + "200g,1,Dash,1/2 tsp,1 tsp,10g,10g,30g,10g,Dash \n",
                    "Source: " + "null",
                    "Image source: " + "null ",
                    "Creative commons confirmed: " + "null",
                    "Date Modified: " + "null\n\n"
                )

                val meal2 = Meal(
                    "Name: " + "Chicken Marengo\n",
                    "Drink Alternative: "+"null\n",
                    "Category: "+"Chicken\n",
                    "Area: "+ "French\n",
                    "Instructions: "+ "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\r\n" +
                            "Pour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \u2013 you shouldn\u2019t need salt." +
                            "Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.\n",
                    "Meal thumb: " + "https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg \n",
                    "Tags: " + "null\n",
                    "Youtube: " +"https://www.youtube.com/watch?v=U33HYUr-0Fw\n",
                    "Ingredients: " +"Olive Oil,Mushrooms,Passata,Chicken Stock Cube,Black Olives,Parsley\n",
                    "Measures: " + "1 tbs,300g,4,500g,1,100g,Chopped\n",
                    "Source: " + "https://www.bbcgoodfood.com/recipes/3146682/chicken-marengo\n",
                    "Image source: " +"null",
                    "Creative commons confirmed: " +"null",
                    "Date Modified: " +"null\n\n"
                )

                val meal3 = Meal(
                    "Name: " + "Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber\n",
                    "Drink Alternative: "+"null\n",
                    "Category: "+"Beef\n",
                    "Area: "+"Vietnamese\n",
                    "Instructions: "+ "Add'l ingredients: mayonnaise, siracha\r\n\r\n1\r\n\r\n" +
                            "Place rice in a fine-mesh sieve and rinse until water runs clear. " +
                            "Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, " +
                            "then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or " +
                            "until ready to serve.\r\n\r\n2\r\n\r\n" +
                            "Meanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). " +
                            "Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. " +
                            "Trim, peel, and grate carrot.\r\n\r\n3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n4\r\n\r\n" +
                            "Heat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n5\r\n\r\n" +
                            "Fluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.\n",
                    "Meal thumb: " + "https://www.themealdb.com/images/media/meals/z0ageb15831895null\n",
                    "Tags: " +"null",
                    "Youtube: " +"null\n",
                    "Ingredients: " +"Rice,Onion,Lime,Garlic Clove,Cucumber,Carrots,Ground Beef,Soy Sauce\n",
                    "Measures: " +"White,1,1,3,1,3oz,1lb,2oz\n",
                    "Source: " +"null",
                    "Image source: " +"null",
                    "Creative commons confirmed: " +"null",
                    "Date Modified: " +"null\n\n"

                )

                val meal4 = Meal(
                    "Name: " + "Leblebi Soup\n",
                    "Drink Alternative: "+"null\n",
                    "Category: "+"Vegetarian\n",
                    "Area: "+"Tunisian\n",
                    "Instructions: "+ "Heat the oil in a large pot. Add the onion and cook until translucent.\r\n" +
                            "Drain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, " +
                            "then reduce the heat and cover. Simmer for 30 minutes.\r\n" +
                            "In the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. " +
                            "Add the garlic and salt and pound to a fine paste.\r\n" +
                            "Add the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\n" +
                            "Season to taste with salt, pepper and lemon juice and serve hot.\n\n",
                    "Meal thumb: " + "https://www.themealdb.com/images/media/meals/x2fw9e1560460636.jpg \n",
                    "Tags: " +"Soup\n",
                    "Youtube: " +"https://www.youtube.com/watch?v=BgRifcCwinY\n",
                    "Ingredients: " +"Olive Oil,Onion, Chickpeas,Vegetable Stock,Cumin,Garlic,Salt,Harissa Spice,Pepper,Lime\n",
                    "Measures: " +"2 tbs,1 medium finely diced,250g,1.5L,1 tsp,5 cloves,1/2 tsp,1 tsp, Pinch,1/2 \n",
                    "Source: " +"http://allrecipes.co.uk/recipe/43419/leblebi--tunisian-chickpea-soup-.aspx \n",
                    "Image source: " +"null",
                    "Creative commons confirmed: " +"null",
                    "Date Modified: " +"null\n\n"
                    )

                // Populating DB
                mealDao.insertMeal(meal1, meal2, meal3, meal4)
                toast.show()

                // Displaying DB table data
                displayDbTablePage()
            }
        }
    }

    // Displays the meal data that saved in the Database
    fun displayDbTablePage() {
        val i = Intent(this, DBTable::class.java)
        startActivity(i)
    }

    //Displaying meal Search by ingredient Page
    fun displaySearchPage() {
        val i = Intent(this, MealsByIngredient::class.java)
        startActivity(i)
    }
    //
    // Displaying meal Search Page
    fun displayMealSearchPage(){
        val i = Intent(this, Search_meals::class.java)
        startActivity(i)
    }
    //
    // Displaying all meal Search Page
    fun displayAllMealPage() {
        val i = Intent(this,All_meals::class.java)
        startActivity(i)
    }
}