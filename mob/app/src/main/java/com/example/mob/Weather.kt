package com.example.mob

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class Weather : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey("city")) {
                getCurrentWeather(bundle.get("city") as String)
            }
            else {
                getCurrentWeather(bundle.get("lat") as Double, bundle.get("long") as Double)
            }
        }

        if (bundle != null) {
            if (bundle.containsKey("city")) {
                findViewById<TextView>(R.id.city).text = bundle.get("city") as String
            }
        }

        //Set a listener on the switch's state (On -> Farenheit / Off -> Celsius)
        val mySwitch = findViewById<Switch>(R.id.switch1)
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                //Change the unit to Farenheit
                findViewById<TextView>(R.id.unit).text = "F"
                findViewById<TextView>(R.id.unitmin).text = "F"
                findViewById<TextView>(R.id.unitmax).text = "F"

                //Convert the temperatures in Celsius to Farenheit
                findViewById<TextView>(R.id.temperature).text =
                    (findViewById<TextView>(R.id.temperature).text
                        .toString().toDouble()
                        .times(1.8)
                        .plus(32))
                        .toFloat().toString()
                findViewById<TextView>(R.id.tempmin).text =
                    (findViewById<TextView>(R.id.tempmin).text
                        .toString().toDouble()
                        .times(1.8)
                        .plus(32))
                        .toFloat().toString()
                findViewById<TextView>(R.id.tempmax).text =
                    (findViewById<TextView>(R.id.tempmax).text
                        .toString().toDouble()
                        .times(1.8)
                        .plus(32))
                        .toFloat().toString()

            } else {
                //Change the unit to Celsius
                findViewById<TextView>(R.id.unit).text = "°C"
                findViewById<TextView>(R.id.unitmin).text = "°C"
                findViewById<TextView>(R.id.unitmax).text = "°C"

                //Convert the temperatures in Farenheit to Celsius
                findViewById<TextView>(R.id.temperature).text =
                    (findViewById<TextView>(R.id.temperature).text
                        .toString().toDouble()
                        .minus(32)
                        .div(1.8))
                        .toFloat().toString()
                findViewById<TextView>(R.id.tempmin).text =
                    (findViewById<TextView>(R.id.tempmin).text
                        .toString().toDouble()
                        .minus(32)
                        .div(1.8))
                        .toFloat().toString()
                findViewById<TextView>(R.id.tempmax).text =
                    (findViewById<TextView>(R.id.tempmax).text
                        .toString().toDouble()
                        .minus(32)
                        .div(1.8))
                        .toFloat().toString()
            }
        }

    }

    //API call for the current weather given in a specified city
    private fun getCurrentWeather (city : String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://api.openweathermap.org/data/2.5/weather?q=$city&appid=dce2cdec98ef72209ff7494806d659a3")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body?.string()
                val json = JSONObject(jsonData)

                runOnUiThread(Runnable {
                    Picasso
                        .get()
                        .load(
                            "https://openweathermap.org/img/wn/" +
                                    json.getJSONArray("weather").getJSONObject(0).get("icon")
                                        .toString()
                                    + "@2x.png"
                        )
                        .into(findViewById<ImageView>(R.id.weatherimage))

                    findViewById<TextView>(R.id.temperature).text = json
                        .getJSONObject("main")
                        .get("temp").toString()
                        .toDouble()
                        .minus(273.15)
                        .toFloat()
                        .toString()

                    findViewById<TextView>(R.id.wind).text = json
                        .getJSONObject("wind")
                        .get("speed")
                        .toString() + " m/s"

                    findViewById<TextView>(R.id.tempmin).text = json
                        .getJSONObject("main")
                        .get("temp_min")
                        .toString()
                        .toDouble()
                        .minus(273.15)
                        .toFloat()
                        .toString()

                    findViewById<TextView>(R.id.tempmax).text = json
                        .getJSONObject("main")
                        .get("temp_max")
                        .toString()
                        .toDouble()
                        .minus(273.15)
                        .toFloat()
                        .toString()

                    findViewById<TextView>(R.id.pression).text = json
                        .getJSONObject("main")
                        .get("pressure")
                        .toString() + " hPa"

                    findViewById<TextView>(R.id.humidity).text = json
                        .getJSONObject("main")
                        .get("humidity")
                        .toString() + "%"
                })
            }

            override fun onFailure(call: Call, e: IOException) {
                println("API execute failed")
            }
        })
    }

    //API call for the current weather given by geographic coordinates
    private fun getCurrentWeather (lat : Double, lon : Double) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&appid=dce2cdec98ef72209ff7494806d659a3")
            .get()
            .build()

        client.newCall(request).enqueue(object: Callback {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body?.string()
                val json = JSONObject(jsonData)
                runOnUiThread(Runnable {
                    val country = json.getJSONObject("sys").get("country").toString()
                    findViewById<TextView>(R.id.city).text = "$country : Latitude $lat; Longitude $lon"
                    Picasso
                        .get()
                        .load("https://openweathermap.org/img/wn/" +
                                json.getJSONArray("weather").getJSONObject(0).get("icon").toString()
                                + "@2x.png")
                        .into(findViewById<ImageView>(R.id.weatherimage))

                    findViewById<TextView>(R.id.temperature).text = json
                        .getJSONObject("main")
                        .get("temp").toString()
                        .toDouble()
                        .minus(273.15)
                        .toFloat()
                        .toString()

                    findViewById<TextView>(R.id.wind).text = json
                        .getJSONObject("wind")
                        .get("speed")
                        .toString()+ " m/s"

                    findViewById<TextView>(R.id.tempmin).text = json
                        .getJSONObject("main")
                        .get("temp_min")
                        .toString()
                        .toDouble()
                        .minus(273.15)
                        .toFloat()
                        .toString()

                    findViewById<TextView>(R.id.tempmax).text = json
                        .getJSONObject("main")
                        .get("temp_max")
                        .toString()
                        .toDouble()
                        .minus(273.15)
                        .toFloat()
                        .toString()

                    findViewById<TextView>(R.id.pression).text = json
                        .getJSONObject("main")
                        .get("pressure")
                        .toString() + " hPa"

                    findViewById<TextView>(R.id.humidity).text = json
                        .getJSONObject("main")
                        .get("humidity")
                        .toString() + "%"
                })
            }

            override fun onFailure(call: Call, e: IOException) {
                println("API execute failed")
            }
        })
    }
}