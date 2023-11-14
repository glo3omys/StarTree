package com.example.startree.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.startree.dao.DiseaseDao
import com.example.startree.databinding.ActivityPlantClassificationBinding
import com.example.startree.entity.ReportEntity
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PlantClassificationActivity : AppCompatActivity() {
    private var mBinding: ActivityPlantClassificationBinding? = null
    private val binding get() = mBinding!!

    private var imageUri : Uri? = null
    private var diseaseCode : Int = -1

    @Inject
    lateinit var diseaseDao: DiseaseDao

    /*@Inject
    lateinit var reportDao: ReportDao*/

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPlantClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUri = intent.getParcelableExtra("imageUri")
        diseaseCode = intent.getIntExtra("diseaseCode", -1)

        binding.btnSaveReport.setOnClickListener {
            if (imageUri != null) {
                saveReport()
                Toast.makeText(this, "SUCCESS: SAVE", Toast.LENGTH_SHORT).show()
            }
            else {
                // Toast
                Toast.makeText(this, "FAIL: SAVE", Toast.LENGTH_SHORT).show()
            }
        }

        makeReport()
    }

    private fun saveReport() {
        val report = ReportEntity(diseaseCode = diseaseCode, imageUri = imageUri.toString())
        reportViewModel.insertReport(report)
    }

    private fun makeReport() {

        lifecycleScope.launch {
            try {
                // disease = loadDisease(diseaseId)
                val disease = withContext(Dispatchers.IO) {
                    diseaseDao.getDiseaseByCode(diseaseCode)
                }

                withContext(Dispatchers.Main) {
                    if (disease != null) {
                        binding.imvPlant.setImageURI(imageUri)
                        binding.tvDiseaseName.text = disease.diseaseName
                        binding.tvDiseaseExplain.text = disease.diseaseExplain
                        binding.tvDiseaseSolution.text = disease.diseaseSolution
                    }
                    else
                        binding.tvDiseaseName.text = "NO SUCH DISEASE EXIST"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}