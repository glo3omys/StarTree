package com.example.startree.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.startree.Report
import com.example.startree.dao.DiseaseDao
import com.example.startree.databinding.ActivityReportLookupBinding
import com.example.startree.entity.DiseaseEntity
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReportLookupActivity : AppCompatActivity() {
    private var mBinding: ActivityReportLookupBinding? = null
    private val binding get() = mBinding!!

    private val reportViewModel: ReportViewModel by viewModels()
    private lateinit var reportLookupAdapter: ReportLookupAdapter

    @Inject
    lateinit var diseaseDao : DiseaseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityReportLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reportLookupAdapter = ReportLookupAdapter(this)
        binding.rvReportsList.adapter = reportLookupAdapter
        // reportLookupAdapter.reports = reportViewModel.reports

        // *** CHECK ***
        reportViewModel.reports.observe(this, Observer { reports ->
            lifecycleScope.launch {
                /*val newList = withContext(Dispatchers.IO) {
                    combineReport(reports)
                }*/
                // reportViewModel.updateReports(reports)
                reportLookupAdapter.updateReports(reports)
            }
        })

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    /*private fun formatTimestamp(timestamp: Long) : String {
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return date.format(formatter)
    }*/

    private fun combineReport(reports: List<Report>): List<Report> {
        return reports.map { report ->
            val diseaseData : DiseaseEntity = diseaseDao.getDiseaseByCode(report.diseaseCode)
            Report(reportId = report.reportId, reportDate = report.reportDate, imageUri = report.imageUri,
                diseaseCode = report.diseaseCode, diseaseName = diseaseData.diseaseName, diseaseExplain = diseaseData.diseaseExplain, diseaseSolution = diseaseData.diseaseSolution)
        }
    }
}