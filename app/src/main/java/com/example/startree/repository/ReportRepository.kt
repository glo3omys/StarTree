package com.example.startree.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.startree.dao.ReportDao
import com.example.startree.entity.ReportEntity
import javax.inject.Inject

class ReportRepository @Inject constructor(private val reportDao: ReportDao) {
    // val reports: MutableLiveData<List<ReportEntity>> = reportDao.getAllReports()
     val reports: LiveData<List<ReportEntity>> = reportDao.getAllReports()

    /*fun getAllReports(): MutableLiveData<ReportEntity> {
        return reportDao.getAll() as MutableLiveData<ReportEntity>
    }*/

    /*init {
        viewModelScope.launch {
            reports.postValue(reportDao.getAllReports())
        }
    }*/

    fun getReportById(reportId: Int): ReportEntity {
        return reportDao.getReportById(reportId)
    }

    /*fun deleteReportById(reportId: Int) {
        reportDao.delete(reportId)
    }*/
    suspend fun deleteReport(report: ReportEntity) {
        reportDao.deleteReport(report)
    }

    suspend fun insertReport(report: ReportEntity) {
        reportDao.insertReport(report)
    }

    suspend fun updateReport(report: ReportEntity) {
        reportDao.updateReport(report)
    }

    suspend fun deleteReportById(id: Int) {
        reportDao.deleteReportById(id)
    }
}