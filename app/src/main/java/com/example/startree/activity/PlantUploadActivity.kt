package com.example.startree.activity

import android.app.Activity
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.graphics.Color
import com.example.startree.LoadingDialog
import com.example.startree.databinding.ActivityPlantUploadBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PlantUploadActivity : AppCompatActivity() {
    private var mBinding: ActivityPlantUploadBinding? = null
    private val binding get() = mBinding!!

    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>
    private val PICK_IMAGE_REQUEST = 1

    private var selectedImageUri : Uri? = null
    private val modelPath: String = "StarTree.tflite"

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
                val loadingDialog = LoadingDialog(this@PlantUploadActivity)
                loadingDialog.show()

                GlobalScope.launch {
                    todo()

                    runOnUiThread {
                        loadingDialog.dismiss()
                    }
                }
            }
        }

        binding.btnModelTest.setOnClickListener {
            val nextIntent = Intent(this, PlantRecognitionActivity::class.java)
            nextIntent.putExtra("imageUri", selectedImageUri)
            startActivity(nextIntent)

            finish()
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

    private fun todo() {
        val input = loadImageAndPreprocess(selectedImageUri)
        var outputs = Array(1) { FloatArray(6) }

        val tflite = getTfliteInterpreter(modelPath)
        tflite?.run(input, outputs)

        val diseaseCode = outputs[0].indexOfFirst { it == outputs[0].maxOrNull() } + 1

        val nextIntent = Intent(this, PlantClassificationActivity::class.java)
        nextIntent.putExtra("imageUri", selectedImageUri)
        nextIntent.putExtra("diseaseCode", diseaseCode)

        startActivity(nextIntent)

        finish()
    }

    /*private fun loadImage(uri: Uri?): ByteBuffer? {
        if (uri == null) {
            // Handle the case where the imageUri is null
            return null
        }

        try {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream == null) {
                // Handle the case where the input stream couldn't be opened
                return null
            }

            // Get the length of the input stream
            val length = inputStream.available()

            // Allocate a ByteBuffer with the size of the input stream
            val buffer = ByteBuffer.allocateDirect(length)

            // Read the input stream into the ByteBuffer
            val bytesRead = inputStream.read(buffer.array())

            if (bytesRead == -1) {
                // Handle the case where the input stream couldn't be read
                return null
            }

            inputStream.close()

            return buffer
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle IOException appropriately
            return null
        }
    }*/

    /*
    // resize: X
    private fun loadImageAndPreprocess(imageUri: Uri?): ByteBuffer? {
        if (imageUri == null) {
            // Handle the case where the imageUri is null
            return null
        }

        try {
            // Open an input stream to read the image data from the specified Uri
            val inputStream = contentResolver.openInputStream(imageUri)
            if (inputStream == null) {
                // Handle the case where the input stream couldn't be opened
                return null
            }

            // Decode the input stream into a Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            if (bitmap == null) {
                // Handle the case where the image couldn't be decoded into a Bitmap
                return null
            }

            // Convert the Bitmap to a ByteBuffer
            val inputImage = ByteBuffer.allocateDirect(3 * bitmap.width * bitmap.height * java.lang.Float.SIZE / java.lang.Byte.SIZE)
            inputImage.order(ByteOrder.nativeOrder())
            val pixels = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

            // Normalize pixel values and add them to the ByteBuffer
            for (pixel in pixels) {
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF

                // Normalize pixel values and add to ByteBuffer
                inputImage.putFloat(r.toFloat() / 255.0f)
                inputImage.putFloat(g.toFloat() / 255.0f)
                inputImage.putFloat(b.toFloat() / 255.0f)
            }

            return inputImage
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle IOException appropriately
            return null
        }
    }*/


    // resize: O
    private fun loadImageAndPreprocess(imageUri: Uri?): ByteBuffer? {
        if (imageUri == null) {
            // Handle the case where the imageUri is null
            return null
        }

        try {
            val inputStream = contentResolver.openInputStream(imageUri)
            if (inputStream == null) {
                // Handle the case where the input stream couldn't be opened
                return null
            }

            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            if (bitmap == null) {
                // Handle the case where the image couldn't be decoded into a Bitmap
                return null
            }

            // Assuming your image size is 256x256 for simplicity
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true)

            // Convert the Bitmap to a ByteBuffer
            val inputImage = ByteBuffer.allocateDirect(3 * 256 * 256 * java.lang.Float.SIZE / java.lang.Byte.SIZE)
            inputImage.order(ByteOrder.nativeOrder())
            val pixels = IntArray(256 * 256)
            resizedBitmap.getPixels(pixels, 0, 256, 0, 0, 256, 256)

            for (pixel in pixels) {
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF

                // Normalize pixel values and add to ByteBuffer
                inputImage.putFloat(r.toFloat() / 255.0f)
                inputImage.putFloat(g.toFloat() / 255.0f)
                inputImage.putFloat(b.toFloat() / 255.0f)
            }

            return inputImage
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle IOException appropriately
            return null
        }
    }

    private fun getTfliteInterpreter(modelPath: String): Interpreter? {
        return try {
            Interpreter(loadModelFile(this, modelPath))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(activity: Activity, modelPath: String): MappedByteBuffer {
        val fileDescriptor: AssetFileDescriptor = activity.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}