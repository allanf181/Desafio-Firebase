package com.example.desafio_firebase

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.desafio_firebase.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = " "
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        binding.tvName.text = intent.getStringExtra("name")
        Glide.with(binding.ivFullGame).asBitmap()
                .load(intent.getStringExtra("img"))
                .into(binding.ivFullGame)
        binding.include.tvName.text = intent.getStringExtra("name")
        binding.include.tvAno.text = intent.getStringExtra("created")
        binding.include.tvDesc.text = intent.getStringExtra("desc")

    }
}