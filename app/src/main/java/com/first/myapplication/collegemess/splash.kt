package com.first.myapplication.collegemess

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        progressbar.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@splash, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            progressbar.visibility = View.GONE
            finish()
        }, 2000)
    }
}