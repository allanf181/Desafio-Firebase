package com.example.desafio_firebase

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio_firebase.databinding.ListGamesBinding
import com.example.desafio_firebase.entities.Game
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class HomeAdapter(options: FirebaseRecyclerOptions<Game>) : FirebaseRecyclerAdapter<Game, HomeAdapter.GameViewHolder>(
    options
) {
    lateinit var binding: ListGamesBinding
    class GameViewHolder(val binding: ListGamesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(game: Game) {
            val name = binding.tvNome
            val create = binding.tvAno
            val img = binding.ivImage
            name.text = game.name
            create.text = game.created
            Glide.with(img).asBitmap()
                .load(game.image)
                .into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        binding = ListGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int, model: Game) {
        holder.bindView(model)
        holder.itemView.setOnClickListener {
            val i = Intent(binding.root.context, GameActivity::class.java)
            i.putExtra("name", model.name)
            i.putExtra("created", model.created)
            i.putExtra("desc", model.desc)
            i.putExtra("img", model.image)
            i.putExtra("id", model.id)
            binding.root.context.startActivity(i)
        }
    }

}