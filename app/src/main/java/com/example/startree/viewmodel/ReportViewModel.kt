package com.example.startree.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startree.entity.ReportEntity
import com.example.startree.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val reportRepository: ReportRepository) : ViewModel() {
    // val reportList : MutableLiveData<ReportEntity> = MutableLiveData()
    val reportList: MutableLiveData<ReportEntity> = reportRepository.getAllReports()

    fun getReportById(id : Int) {
        reportRepository.getReportById(id)
    }
    fun insertReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.insertReport(report)
    }
    fun deleteReport(report: ReportEntity) = viewModelScope.launch {
        reportRepository.deleteReport(report)
    }
}