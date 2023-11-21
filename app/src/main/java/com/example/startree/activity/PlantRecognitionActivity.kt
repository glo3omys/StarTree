package com.example.startree.activity

import android.app.Activity
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.startree.LoadingDialog
import com.example.startree.R
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PlantRecognitionActivity : AppCompatActivity() {
    private var input: ByteBuffer? = null
    private var outputs = Array(1) { FloatArray(6) }
    private val modelPath: String = "StarTree.tflite"

    private lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_recognition)

        selectedImageUri = intent.getParcelableExtra("imageUri")!!
        input = loadImageAndPreprocess(selectedImageUri)

        todo()
    }

    private fun todo() {
        if (input == null) {
            finish()
        }

        val tflite = getTfliteInterpreter(modelPath)
        tflite?.run(input, outputs)

        val diseaseCode = outputs[0].indexOfFirst { it == outputs[0].maxOrNull() } + 1

        val nextIntent = Intent(this, PlantClassificationActivity::class.java)
        nextIntent.putExtra("imageUri", selectedImageUri)
        nextIntent.putExtra("diseaseCode", diseaseCode)


        startActivity(nextIntent)

        finish()
    }

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