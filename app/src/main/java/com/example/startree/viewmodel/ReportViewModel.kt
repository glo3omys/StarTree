package com.example.startree.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startree.entity.ReportEntity
import com.example.startree.repository.DiseaseRepository
import com.example.startree.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val reportRepository: ReportRepository, private val diseaseRepository: DiseaseRepository) : ViewModel() {
    private var reportList = mutableListOf<ReportEntity>()
    private val _reports = MutableLiveData<List<ReportEntity>>()
    val reports: LiveData<List<ReportEntity>> get() = _reports

    init {
        loadReportsFromRepository()
    }

    private fun loadReportsFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            reportList = reportRepository.getAllReports().toMutableList()
            _reports.postValue(reportList)
        }
    }

    fun getReportById(id : Int) {
        reportRepository.getReportById(id)
    }
    fun insertReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.insertReport(report)
        reportList.add(report)
        _reports.postValue(reportList)
    }
    fun deleteReportById(id: Int) = viewModelScope.launch {
        reportRepository.deleteReportById(id)
        reportList.removeIf { it.reportId == id }
        _reports.postValue(reportList)
    }
    /*fun updateReports(reports: List<ReportEntity>) {
        // reports.postValue(newList)
        // this.reports = reports // as LiveData<List<ReportEntity>>
    }*/
}