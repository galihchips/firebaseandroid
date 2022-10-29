package com.upj.auth

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddCatalog : AppCompatActivity() {
    private lateinit var tvTitle : TextView
    private lateinit var tvDesc : TextView
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_ID = "extra_id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_book)
//        tvTitle.setText(id)
//        tvDesc.setText(name)
    }


}