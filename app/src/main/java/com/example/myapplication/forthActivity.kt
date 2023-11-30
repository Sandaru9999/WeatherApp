package com.example.myapplication
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class forthActivity : AppCompatActivity() {

    private val apiKey = "dc6d0c4421d5cbac3e13f7785dbbef38"

    private lateinit var locationAutoCompleteTextView: AutoCompleteTextView
    private lateinit var searchButton: Button
    private lateinit var forecastTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forth)

        val Backbutton = findViewById<Button>(R.id.backbtn)
        Backbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        locationAutoCompleteTextView = findViewById(R.id.locationAutoCompleteTextView)
        searchButton = findViewById(R.id.searchButton)
        forecastTextView = findViewById(R.id.forecastTextView)

        // Set up adapter for AutoCompleteTextView
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            arrayOf("City1", "City2", "City3") // Add your own list of cities
        )
        locationAutoCompleteTextView.setAdapter(adapter)

        searchButton.setOnClickListener {
            val selectedLocation = locationAutoCompleteTextView.text.toString()
            if (selectedLocation.isNotEmpty()) {
                WeatherTask().execute(selectedLocation)
            }
        }
    }

    inner class WeatherTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            val response: String?
            try {
                val city = params[0] ?: return null
                // API URL for 5-day weather forecast
                val apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=$apiKey"

                // Make the API call and get the response
                response = URL(apiUrl).readText(Charsets.UTF_8)
            } catch (e: Exception) {
                return null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                // Parse the JSON response
                val jsonObj = JSONObject(result)
                val list = jsonObj.getJSONArray("list")

                // Display the forecast for the next 4 days
                forecastTextView.text = "" // Clear previous forecast
                for (i in 0 until 4) {
                    val day = list.getJSONObject(i * 8) // Data for every 3 hours, so skip to the next day
                    val main = day.getJSONObject("main")
                    val temp = main.getString("temp")

                    val dateText = getDateText(day.getString("dt"))
                    val forecast = "$dateText: $temp °C"

                    // Append forecast to the TextView
                    forecastTextView.append("$forecast\n")
                }

                // Display the selected location
                forecastTextView.append("Location: ${locationAutoCompleteTextView.text}")

            } catch (e: Exception) {
                // Handle exception (e.g., JSON parsing error)
            }
        }

        private fun getDateText(timestamp: String): String {
            // Convert timestamp to date format (you can customize the format)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = Date(timestamp.toLong() * 1000)
            return sdf.format(date)
        }
    }
}
