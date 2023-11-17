package com.example.startree.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.startree.entity.ReportEntity

@Dao
interface ReportDao {
    @Query("SELECT * FROM report ORDER BY reportDate DESC")
    fun getAllReports(): List<ReportEntity>

    @Query("SELECT * FROM report WHERE reportId = :reportId")
    fun getReportById(reportId: Int): ReportEntity

    @Insert
    suspend fun insertReport(vararg reportEntity: ReportEntity)

    @Delete
    suspend fun deleteReport(reportEntity: ReportEntity)

    @Update
    suspend fun updateReport(reportEntity: ReportEntity)

    @Query("DELETE FROM report WHERE reportId = :reportId")
    suspend fun deleteReportById(reportId: Int)
}