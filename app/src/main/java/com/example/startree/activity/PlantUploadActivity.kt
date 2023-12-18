package com.example.startree.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Color
import android.provider.MediaStore
import com.example.startree.ApiServiceGenerator
import com.example.startree.LoadingDialog
import com.example.startree.databinding.ActivityPlantUploadBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PlantUploadActivity : AppCompatActivity() {
    private var mBinding: ActivityPlantUploadBinding? = null
    private val binding get() = mBinding!!

    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

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

        binding.btnPlantDiseaseDetection.setOnClickListener {
            if (binding.imvPlant.drawable == null) {
                Toast.makeText(this, "나뭇잎 사진을 올려주세요", Toast.LENGTH_SHORT).show()
            } else {
                // if an image is selected
                // start loadingDialog to indicate that the processing is in progress
                val loadingDialog = LoadingDialog(this@PlantUploadActivity)
                loadingDialog.show()

                GlobalScope.launch {
                    callApiForPrediction()

                    runOnUiThread {
                        loadingDialog.dismiss()
                    }
                }
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

    private fun callApiForPrediction() {
        val apiServiceGenerator = ApiServiceGenerator()

        // create a MultipartBody.Part using the client's image to communicate with FastAPI
        val multipartFile: MultipartBody.Part? = if (selectedImageUri != null) {
            val path = getAbsolutePath(selectedImageUri, this)
            val file = File(path)
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            MultipartBody.Part.createFormData("file", file.name, requestFile)
        } else {
            null
        }

        if (multipartFile != null) {
            val call = apiServiceGenerator.service.postPredict(multipartFile)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val resultJsonString = response.body()!!.string()
                        val outputs = JSONObject(resultJsonString).getJSONArray("predictions")

                        // get diseaseCode
                        // based on the index of the maximum float value returned by the server
                        var maxValue = outputs.getDouble(0)
                        var diseaseCode = 0
                        for (i in 1 until outputs.length()) {
                            val currentValue = outputs.getDouble(i)
                            if (currentValue > maxValue) {
                                maxValue = currentValue
                                diseaseCode = i
                            }
                        }
                        // increment 'diseaseCode' by 1,
                        // as indices start from 0 while disease codes start from 1
                        diseaseCode += 1

                        // start PlantClassificationActivity
                        val nextIntent = Intent(
                            this@PlantUploadActivity,
                            PlantClassificationActivity::class.java
                        )
                        nextIntent.putExtra("imageUri", selectedImageUri)
                        nextIntent.putExtra("diseaseCode", diseaseCode)

                        startActivity(nextIntent)

                        finish()
                    } else {
                        Toast.makeText(
                            this@PlantUploadActivity,
                            "Code: ${response.code()}, Msg: ${response.message()}}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        this@PlantUploadActivity,
                        "FAIL: SERVER ACCESS DENIED",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    // converts the image URI obtained through the ActivityResultLauncher<Intent> to its absolute file path
    private fun getAbsolutePath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
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