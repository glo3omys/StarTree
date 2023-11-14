package com.example.startree.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.startree.dao.DiseaseDao
import com.example.startree.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {
    private var mBinding: ActivityTestBinding? = null
    private val binding get() = mBinding!!

    @Inject
    lateinit var diseaseDao : DiseaseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            try {
                val disease = withContext(Dispatchers.IO) {
                    diseaseDao.getDiseaseByCode(5)
                }

                withContext(Dispatchers.Main) {
                    if (disease != null) {
                        binding.tvDiseaseName.text = disease.diseaseName
                        binding.tvDiseaseExplain.text = disease.diseaseExplain
                        binding.tvDiseaseSolution.text = disease.diseaseSolution
                    }
                    else
                        binding.tvDiseaseName.text = "NOT FOUND"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}