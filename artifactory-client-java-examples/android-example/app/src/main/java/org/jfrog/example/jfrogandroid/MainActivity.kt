package org.jfrog.example.jfrogandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jfrog.example.jfrogandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}