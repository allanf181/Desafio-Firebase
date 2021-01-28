package com.example.desafio_firebase

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
//            val i = Intent(context, ComicActivity::class.java)
//            i.putExtra("title", comic.title)
//            i.putExtra("img_back", comic.images[0].getFullUrl())
//            i.putExtra("img_hq", comic.thumbnail.getFullUrl())
//            i.putExtra("desc", comic.description)
//            i.putExtra("price", comic.prices[0].price)
//            i.putExtra("pages", comic.pageCount)
//            i.putExtra("date", comic.dates[0].getFormattedDate())
//            context.startActivity(i)
        }
    }

}