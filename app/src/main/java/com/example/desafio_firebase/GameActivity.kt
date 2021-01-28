package com.example.desafio_firebase

import android.content.Intent
import android.os.Bundle
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
            val i = Intent(binding.root.context, EditGameActivity::class.java)
            i.putExtras(intent.extras!!)
            startActivityForResult(i,50)
        }

        parseIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 50) {
            if (resultCode == RESULT_OK) {
                parseIntent(data!!)
            }
        }
    }

    fun parseIntent(intent: Intent){
        binding.tvName.text = intent.getStringExtra("name")
        Glide.with(binding.ivFullGame).asBitmap()
                .load(intent.getStringExtra("img"))
                .into(binding.ivFullGame)
        binding.include.tvName.text = intent.getStringExtra("name")
        binding.include.tvAno.text = intent.getStringExtra("created")
        binding.include.tvDesc.text = intent.getStringExtra("desc")
    }
}