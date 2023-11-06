package com.example.startree.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disease")
data class DiseaseEntity(
    @PrimaryKey(autoGenerate = true) val diseaseCode: Int,
    @ColumnInfo(name = "diseaseName") val diseaseName: String,
    @ColumnInfo(name = "diseaseExplain") val diseaseExplain: String,
    @ColumnInfo(name = "diseaseSolution") val diseaseSolution: String,
)
