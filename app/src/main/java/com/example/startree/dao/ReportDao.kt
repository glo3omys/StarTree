package com.example.startree.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.startree.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * FROM report ORDER BY reportDate DESC")
    fun getAll(): Flow<List<ReportEntity>>

    @Query("SELECT * FROM report WHERE reportId = :reportId")
    fun getReportById(reportId: Int): ReportEntity

    @Insert
    suspend fun insertReport(vararg reportEntity: ReportEntity)

    @Delete
    suspend fun deleteReport(reportEntity: ReportEntity)
}