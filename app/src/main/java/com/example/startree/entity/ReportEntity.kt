package com.example.startree.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Int? = null,
    @ColumnInfo(name = "reportDate") var reportDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "diseaseCode") val diseaseCode: Int,
    @ColumnInfo(name = "imageUri") val imageUri: String,
)