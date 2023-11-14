package com.example.startree.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.PreferenceUtil
import com.example.startree.InitializeDatabase
import com.example.startree.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    lateinit var prefs : PreferenceUtil

    @Inject
    lateinit var initializeDatabase: InitializeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferenceUtil(applicationContext)

        // if first run
        val initialized = prefs.getSharedPrefs("initialized", "false")
        if (initialized == "false") {
            CoroutineScope(Dispatchers.IO).launch {
                initializeDatabase.parseAndInsertData()
            }
            prefs.setSharedPrefs("initialized", "true")
        }

        binding.btnGoToUpload.setOnClickListener {
            val nextIntent = Intent(this, PlantUploadActivity::class.java)
            startActivity(nextIntent)
        }

        binding.btnGoToReportLookup.setOnClickListener {
            val nextIntent = Intent(this, ReportLookupActivity::class.java)
            startActivity(nextIntent)
        }

        binding.btnTest.setOnClickListener {
            val nextIntent = Intent(this, TestActivity::class.java)
            startActivity(nextIntent)
        }
    }
}