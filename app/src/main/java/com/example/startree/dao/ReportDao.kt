package com.example.startree.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.startree.entity.ReportEntity

@Dao
interface ReportDao {
    @Query("SELECT * FROM report")
    fun getAll(): List<ReportEntity>

    /*@Insert
    fun insertAll(vararg users: User)*/

    @Delete
    fun delete(reportEntity: ReportEntity)
}