package com.example.startree.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.startree.R
import com.example.startree.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToUpload.setOnClickListener {
            val nextIntent = Intent(this, PlantUploadActivity::class.java)
            startActivity(nextIntent)
        }

        binding.btnGoToReportLookup.setOnClickListener {
            val nextIntent = Intent(this, ReportLookupActivity::class.java)
            startActivity(nextIntent)
        }

    }
}