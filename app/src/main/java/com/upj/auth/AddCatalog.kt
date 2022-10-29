package com.upj.auth

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddCatalog : AppCompatActivity() {
    private lateinit var tvTitle : TextView
    private lateinit var tvDesc : TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_book)
        tvTitle = findViewById(R.id.titleBookDetail)
        tvDesc = findViewById(R.id.descBookDetail)
        val title = intent.extras!!.getString("title")
        val desc = intent.extras!!.getString("desc")
        tvTitle.setText(title)
        tvDesc.setText(desc)
        Log.d("tittttleee" , desc.toString())

//        tvTitle.setText(id)
//        tvDesc.setText(name)
    }


}