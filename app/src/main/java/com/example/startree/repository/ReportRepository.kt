package com.example.startree.repository

import com.example.startree.dao.ReportDao
import com.example.startree.entity.ReportEntity
import javax.inject.Inject

class ReportRepository @Inject constructor(private val reportDao: ReportDao) {
    fun getAllReports(): List<ReportEntity> {
        return reportDao.getAllReports()
    }

    fun getReportById(reportId: Int): ReportEntity {
        return reportDao.getReportById(reportId)
    }

    suspend fun deleteReportById(id: Int) {
        reportDao.deleteReportById(id)
    }

    suspend fun insertReport(report: ReportEntity) {
        reportDao.insertReport(report)
    }

    suspend fun updateReport(report: ReportEntity) {
        reportDao.updateReport(report)
    }


}