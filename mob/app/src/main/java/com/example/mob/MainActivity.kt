package com.example.mob

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStream


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val my_cities : MutableList<String> = ArrayList()
    private val MAIN_CITIES = arrayOf(
        "Tokyo", "Jakarta", "Delhi", "Manila", "Seoul","Shanghai","Karachi","Beijing","New York",
        "Guangzhou","Sao Paulo","Mexico","Mumbai","Kyoto","Moscow","Dhaka","Cairo","Los Angeles",
        "Bangkok","Kolkata","Buenos Aires","Tehran","Istanbul","Lagos","Shenzhen","Rio de Janeiro",
        "Kinshasa","Tianjin","Paris","Lima","Chengdu","London","Nagoya", "Lahore","Bangalore",
        "Chennai","Chicago","Bogota","Ho Chi Minh City","Hyderabad","Dongguan","Johannesburg",
        "Wuhan","Taipei","Hangzhou","Hong Kong","Chongqing","Ahmadabad","Kuala Lumpur","Quanzhou",
        "Essen-Dusseldorf", "Baghdad","Toronto","Santiago","Dallas","Madrid","Nanjing","Shenyang",
        "Xi'an","San Francisco","Luanda","Qingdao","Houston","Miami","Bandung","Riyadh","Pune",
        "Singapore", "Philadelphia","Surat","Milan","Suzhou","St. Petersburg","Khartoum","Atlanta",
        "Zhengzhou", "Washington","Surabaya","Harbin","Abidjan","Yangon","Nairobi","Barcelona",
        "Alexandria", "Kabul","Guadalajara","Ankara","Belo Horizonte", "Boston","Xiamen","Kuwait",
        "Dar es Salaam","Phoenix","Dalian","Accra","Monterrey","Berlin","Sydney","Fuzhou","Medan",
        "Dubai","Melbourne","Rome","Busan","Cape Town","Jinan","Ningbo", "Hanoi","Naples","Taiyuan",
        "Jiddah","Detroit","Hefei","Changsha","Kunming","Wuxi","Medellín","Faisalabad","Aleppo",
        "Kano","Montréal","Dakar","Athens"
    )

    override fun onDestroy() {
        super.onDestroy()

        val json = Gson().toJson(my_cities)

        

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ptext = findViewById<View>(R.id.ptext) as AutoCompleteTextView
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, MAIN_CITIES
        )

        ptext.setAdapter(adapter)
        val save = findViewById<Button>(R.id.save)
        save.setOnClickListener() {
            if (ptext.text.toString().isNotEmpty()) {
                my_cities.add(ptext.text.toString())
                ptext.text.clear()
            }
        }

        my_cities.add("")

        val select = findViewById<Spinner>(R.id.spinner)

        select.setOnItemSelectedListener(this)

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, my_cities)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select.setAdapter(dataAdapter);
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if (parent.getItemAtPosition(position).toString().isNotEmpty()) {
            // On selecting a spinner item
            var item = parent.getItemAtPosition(position).toString()

            // Showing selected spinner item if not empty
            Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_LONG).show()
            val send = findViewById<Button>(R.id.send)
            send.setOnClickListener() {
                val intent = Intent(this, Weather::class.java)
                intent.putExtra("city", item)
                startActivity(intent)
            }
            val delete = findViewById<Button>(R.id.delete)
            delete.setOnClickListener() {
                if (item.isNotEmpty()) {
                    my_cities.remove(item)
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
        val send = findViewById<Button>(R.id.send)
        send.isEnabled = false
        send.setTextColor(R.color.white)
        send.setBackgroundColor(R.color.grey)
    }

}
