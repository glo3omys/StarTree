package com.example.startree

data class Report(
    val reportId: Int,
    val reportDate: Int,
    val imageUri: String,
    val diseaseCode: Int,
    val diseaseExplain: String,
    val diseaseSolution: String,
)
