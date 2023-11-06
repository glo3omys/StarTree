package com.example.startree.repository

import androidx.lifecycle.MutableLiveData
import com.example.startree.dao.ReportDao
import com.example.startree.entity.ReportEntity
import javax.inject.Inject

class ReportRepository @Inject constructor(private val reportDao: ReportDao) {
    //val reportList: Flow<List<ReportEntity>> = reportDao.getAll()
    fun getAllReports(): MutableLiveData<ReportEntity> {
        return reportDao.getAll() as MutableLiveData<ReportEntity>
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
}