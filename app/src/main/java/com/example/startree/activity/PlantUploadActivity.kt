package com.example.startree.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.startree.databinding.ActivityPlantUploadBinding

class PlantUploadActivity : AppCompatActivity() {
    private var mBinding: ActivityPlantUploadBinding? = null
    private val binding get() = mBinding!!

    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPlantUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSelectImage.setOnClickListener {
            openGallery()
        }

        binding.btnGoToRecognition.setOnClickListener {

        }

        selectImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data

                attachImageToImv(selectedImageUri)
            }
        }
    }

    private fun attachImageToImv(imageUri: Uri?) {
        if (imageUri != null) {
            binding.imvPlant.setImageURI(imageUri)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }
}