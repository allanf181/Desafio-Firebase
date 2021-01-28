package com.example.desafio_firebase

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.desafio_firebase.databinding.ActivityEditGameBinding
import com.example.desafio_firebase.entities.Game
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
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

        binding.btnSave.setOnClickListener {
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
                Log.w("aa", "$uuidimg.jpg")
                storageRef.child("$uuidimg.jpg").downloadUrl.addOnSuccessListener {
                    val  database = FirebaseDatabase.getInstance();

                    val ref: DatabaseReference = database.getReference("games")

                    val usersRef = ref

                    usersRef.child(uuidgame).setValue(Game(binding.valueName.text.toString(),binding.valueCreated.text.toString(), binding.valueDesc.text.toString(), it.toString()))
                }.addOnFailureListener {
                    // Handle any errors
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