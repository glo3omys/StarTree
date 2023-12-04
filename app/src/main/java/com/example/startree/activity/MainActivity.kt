package com.example.startree.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.PreferenceUtil
import com.example.startree.InitializeDatabase
import com.example.startree.dao.DiseaseDao
import com.example.startree.databinding.ActivityMainBinding
import com.example.startree.diseases
import com.example.startree.entity.DiseaseEntity
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

    @Inject
    lateinit var diseaseDao: DiseaseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = PreferenceUtil(applicationContext)

        binding.btnGoToUpload.setOnClickListener {
            val nextIntent = Intent(this, PlantUploadActivity::class.java)
            startActivity(nextIntent)
        }

        binding.btnGoToReportLookup.setOnClickListener {
            val nextIntent = Intent(this, ReportLookupActivity::class.java)
            startActivity(nextIntent)
        }

        loadDiseases()
    }

    private fun loadDiseases() {
        CoroutineScope(Dispatchers.IO).launch {
            diseases = diseaseDao.getAllDiseases() as MutableList<DiseaseEntity>
        }
    }
}