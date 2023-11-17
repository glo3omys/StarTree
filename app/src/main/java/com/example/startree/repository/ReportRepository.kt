package com.example.startree.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.startree.dao.ReportDao
import com.example.startree.entity.ReportEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReportRepository @Inject constructor(private val reportDao: ReportDao) {
    // val reports: MutableLiveData<List<ReportEntity>> = reportDao.getAllReports()

    //val reports: List<ReportEntity> = reportDao.getAllReports()

    fun getAllReports(): List<ReportEntity> {
        return reportDao.getAllReports()
    }

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