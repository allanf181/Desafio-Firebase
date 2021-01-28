package com.example.desafio_firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio_firebase.databinding.ActivityMainBinding
import com.example.desafio_firebase.entities.Game
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.floatingActionButton.setOnClickListener {
            val i = Intent(this@MainActivity, EditGameActivity::class.java)
            startActivity(i)
        }
        binding.rv.adapter = HomeAdapter(
            FirebaseRecyclerOptions.Builder<Game>()
                .setQuery(
                    FirebaseDatabase.getInstance()
                    .reference
                    .child("games")
                    .limitToLast(50), Game::class.java)
                .build()).apply { startListening() }


    }
}