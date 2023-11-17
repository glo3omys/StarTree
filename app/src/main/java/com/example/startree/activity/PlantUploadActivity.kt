package com.example.startree.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Color
import com.example.startree.databinding.ActivityPlantUploadBinding
import kotlin.random.Random

class PlantUploadActivity : AppCompatActivity() {
    private var mBinding: ActivityPlantUploadBinding? = null
    private val binding get() = mBinding!!

    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>
    private val PICK_IMAGE_REQUEST = 1

    private var selectedImageUri : Uri? = null

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
            if (binding.imvPlant.drawable == null) {
                Toast.makeText(this, "나뭇잎 사진을 올려주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val nextIntent = Intent(this, PlantClassificationActivity::class.java)
                nextIntent.putExtra("imageUri", selectedImageUri)
                val diseaseCode = Random.nextInt(1, 7)
                nextIntent.putExtra("diseaseCode", diseaseCode)

                startActivity(nextIntent)

                /* *** CHECK*** */
                finish()
            }
        }

        selectImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data

                attachImageToImv(selectedImageUri)
            }
        }
    }

    private fun attachImageToImv(imageUri: Uri?) {
        if (imageUri != null) {
            binding.imvPlant.setImageURI(imageUri)
            binding.imvPlant.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }
}