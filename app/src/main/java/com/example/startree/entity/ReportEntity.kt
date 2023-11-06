package com.example.startree.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Int,
    @ColumnInfo(name = "reportDate") var reportDate: Int,
    @ColumnInfo(name = "diseaseCode") val diseaseCode: Int,
    @ColumnInfo(name = "imageUri") val imageUri: String,
)