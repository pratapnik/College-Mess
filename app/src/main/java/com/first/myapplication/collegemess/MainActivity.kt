package com.first.myapplication.collegemess

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        btnAdminPanel.setOnClickListener {
            val adminLoginIntent = Intent(this, admin_home::class.java)
            startActivity(adminLoginIntent)
        }

        btnMessMenu.setOnClickListener {
            val messMenuIntent = Intent(this, loginActivity::class.java)
            startActivity(messMenuIntent)
        }
    }
}