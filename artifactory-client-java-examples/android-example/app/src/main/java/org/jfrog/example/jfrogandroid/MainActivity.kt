package org.jfrog.example.jfrogandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jfrog.example.jfrogandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonArtifactory.setOnClickListener {
            JFrogHelper.checkArtifactoryDialog(this)
        }
    }

}