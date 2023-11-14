package com.example.startree.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.startree.MyDialog
import com.example.startree.Report
import com.example.startree.databinding.ActivityReportDetailBinding
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportDetailActivity : AppCompatActivity() {
    private var mBinding: ActivityReportDetailBinding? = null
    private val binding get() = mBinding!!

    // lateinit var report: Report
    var diseaseCode: Int = -1
    var reportId: Int = -1

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // report = intent.getParcelableExtra("report")!!
        diseaseCode = intent.getIntExtra("diseaseCode", -1)
        reportId = intent.getIntExtra("reportId", -1)

        initViews()

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnDelete.setOnClickListener {
            val myDialog = MyDialog(this)
            myDialog.deleteReport() {delete ->
                if (delete)
                    deleteReport()
            }
        }
    }

    private fun deleteReport() {
        lifecycleScope.launch {
            reportViewModel.deleteReportById(reportId)
            // reportViewModel.deleteReportById(diseaseCode)
            // reportViewModel.reports.postValue()
            finish()
        }
    }

    private fun initViews() {
        /*binding.imvPlant.setImageURI(Uri.parse(report.imageUri))
        binding.tvDiseaseName.text = report.diseaseName
        binding.tvDiseaseExplain.text = report.diseaseExplain
        binding.tvDiseaseSolution.text = report.diseaseSolution*/
        binding.tvDiseaseName.text = reportId.toString()
    }
}