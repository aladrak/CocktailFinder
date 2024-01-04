package com.example.cocktail_finder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cocktail_finder.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ListFragment())
            .commit()
    }
}