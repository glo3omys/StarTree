package com.example.startree.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startree.Report
import com.example.startree.entity.DiseaseEntity
import com.example.startree.entity.ReportEntity
import com.example.startree.getDateFromLong
import com.example.startree.repository.DiseaseRepository
import com.example.startree.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val reportRepository: ReportRepository, private val diseaseRepository: DiseaseRepository) : ViewModel() {
    var reports: LiveData<List<ReportEntity>> = reportRepository.reports
    /*private val _reports = MutableLiveData<List<Report>>()
    val reports: LiveData<List<Report>> get() = _reports*/

    /*private val _reports = reportRepository.reports
    val reports: LiveData<List<Report>> = Transformations.map(_reports) {reportEntities ->
        combineEntities(reportEntities)
    }*/

    /*private val _reports = reportRepository.reports
    val reports: LiveData<List<Report>> = Transformations.map(_reports) {reportEntities ->
        combineEntities(reportEntities)
    }*/

    /*private val _reports = reportRepository.reports
    val reports: MutableLiveData<List<Report>> = Transformations.map(_reports) { reportEntities ->
        combineEntities(reportEntities)
    }*/

    private fun combineEntities(reports: List<ReportEntity>): List<Report> {
        return reports.map { report ->
            val diseaseData : DiseaseEntity = diseaseRepository.getDiseaseByCode(report.diseaseCode)
            Report(
                reportId = report.reportId,
                reportDate = getDateFromLong(report.reportDate),
                imageUri = report.imageUri,
                diseaseCode = report.diseaseCode,
                diseaseName = diseaseData.diseaseName,
                diseaseExplain = diseaseData.diseaseExplain,
                diseaseSolution = diseaseData.diseaseSolution
            )
        }
    }

    fun getAll(): LiveData<List<ReportEntity>> {
        return reportRepository.reports
    }

    fun getReportById(id : Int) {
        reportRepository.getReportById(id)
    }
    fun insertReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.insertReport(report)
    }
    fun deleteReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.deleteReport(report)
        // reports = reportRepository.reports
    }
    fun deleteReportById(id: Int) = viewModelScope.launch {
        reportRepository.deleteReportById(id)
        // reports.postValue()
    }
    /*fun updateReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.updateReport(report)
        _reports.postValue(report)
    }*/

    fun updateReports(reports: List<ReportEntity>) {
        // reports.postValue(newList)
        // this.reports = reports // as LiveData<List<ReportEntity>>
    }
}