package com.example.startree.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startree.Report
import com.example.startree.entity.DiseaseEntity
import com.example.startree.entity.ReportEntity
import com.example.startree.getDateFromLong
import com.example.startree.repository.DiseaseRepository
import com.example.startree.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val reportRepository: ReportRepository, private val diseaseRepository: DiseaseRepository) : ViewModel() {
    // var reports: LiveData<List<ReportEntity>> = reportRepository.reports
    /*val reports: MutableLiveData<List<ReportEntity>> by lazy {
        MutableLiveData<List<ReportEntity>>()
    }*/

    private var reportList = mutableListOf<ReportEntity>()
    private val _reports = MutableLiveData<List<ReportEntity>>()
    val reports: LiveData<List<ReportEntity>> get() = _reports

    /*private var diseaseList = mutableListOf<DiseaseEntity>()
    private val _diseases = MutableLiveData<List<DiseaseEntity>>()
    val diseases: LiveData<List<DiseaseEntity>> get() = _diseases*/

    init {
        /*viewModelScope.launch {
            reportRepository.reports.observeForever { newReports ->
                _reports.postValue(newReports)
            }
        }*/

            //loadDiseasesFromRepository()
            loadReportsFromRepository()
    }

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

    private fun loadReportsFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            /*val reports = reportRepository.getAllReports()
            _reports.postValue(reports)
            // Log.d("repository", "loadReportsFromRepository") // DONE*/

            reportList = reportRepository.getAllReports().toMutableList()
            _reports.postValue(reportList)
        }
    }
    /*private fun loadDiseasesFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            diseaseList = diseaseRepository.getAllDiseases().toMutableList()
            _diseases.postValue(diseaseList)
        }
    }*/

    fun getReportById(id : Int) {
        reportRepository.getReportById(id)
    }
    fun insertReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.insertReport(report)
        reportList.add(report)
        _reports.postValue(reportList)
    }
    /*fun deleteReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.deleteReport(report)
        loadReportsFromRepository()
        // reports = reportRepository.reports
    }*/
    fun deleteReportById(id: Int) = viewModelScope.launch {
        reportRepository.deleteReportById(id)
        reportList.removeIf { it.reportId == id }
        _reports.postValue(reportList)
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