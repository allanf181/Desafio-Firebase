package com.example.desafio_firebase

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.desafio_firebase.databinding.ActivityEditGameBinding
import com.example.desafio_firebase.entities.Game
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class EditGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditGameBinding
    private lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivGame.setOnClickListener {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Selecione a imagem")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            startActivityForResult(chooserIntent, 1)
        }

        if (intent.hasExtra("id")){
            Glide.with(binding.ivGame).load(intent.getStringExtra("img"))
                    .into(binding.ivGame)
            binding.valueName.setText(intent.getStringExtra("name"))
            binding.valueCreated.setText(intent.getStringExtra("created"))
            binding.valueDesc.setText(intent.getStringExtra("desc"))
        }

        binding.btnSave.setOnClickListener {
            if (intent.hasExtra("id")){
                val uuidgame = intent.getStringExtra("id")!!
                val i = Intent()
                if(this::uri.isInitialized){
                    val uuidimg = UUID.randomUUID().toString()
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.getReferenceFromUrl("gs://desafio---firebase.appspot.com/")
                    val mountainsRef = storageRef.child("$uuidimg.jpg")
                    val uploadTask = mountainsRef.putFile(uri)
                    uploadTask.addOnFailureListener {
                        // Handle unsuccessful uploads
                    }.addOnSuccessListener { taskSnapshot ->
                        storageRef.child("$uuidimg.jpg").downloadUrl.addOnSuccessListener {
                            val  database = FirebaseDatabase.getInstance();
                            val ref: DatabaseReference = database.getReference("games")
                            val gamesRef = ref
                            val model = Game(binding.valueName.text.toString(), binding.valueCreated.text.toString(), binding.valueDesc.text.toString(), it.toString(), uuidgame)
                            i.putExtra("name", model.name)
                            i.putExtra("created", model.created)
                            i.putExtra("desc", model.desc)
                            i.putExtra("img", model.image)
                            i.putExtra("id", model.id)
                            gamesRef.child(uuidgame).setValue(model)
                        }.addOnFailureListener {
                            // Handle any errors
                        }
                    }
                } else {
                    val  database = FirebaseDatabase.getInstance();
                    val ref: DatabaseReference = database.getReference("games")
                    val gamesRef = ref
                    val model = Game(binding.valueName.text.toString(), binding.valueCreated.text.toString(), binding.valueDesc.text.toString(), intent.getStringExtra("img")!!, uuidgame)
                    i.putExtra("name", model.name)
                    i.putExtra("created", model.created)
                    i.putExtra("desc", model.desc)
                    i.putExtra("img", model.image)
                    i.putExtra("id", model.id)
                    gamesRef.child(uuidgame).setValue(model)
                }

                setResult(RESULT_OK, i)
            } else {
                val uuidimg = UUID.randomUUID().toString()
                val uuidgame = UUID.randomUUID().toString()
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.getReferenceFromUrl("gs://desafio---firebase.appspot.com/")
                val mountainsRef = storageRef.child("$uuidimg.jpg")

                val uploadTask = mountainsRef.putFile(uri)

                //val uploadTask = mountainsRef.putBytes(data);
                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    storageRef.child("$uuidimg.jpg").downloadUrl.addOnSuccessListener {
                        val  database = FirebaseDatabase.getInstance();

                        val ref: DatabaseReference = database.getReference("games")

                        val gamesRef = ref

                        gamesRef.child(uuidgame).setValue(Game(binding.valueName.text.toString(), binding.valueCreated.text.toString(), binding.valueDesc.text.toString(), it.toString(), uuidgame))
                    }.addOnFailureListener {
                        // Handle any errors
                    }
                }
            }
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            Glide.with(binding.ivGame).load(data?.getData())
                    .into(binding.ivGame)
            uri = data?.getData()!!
        }
    }
}