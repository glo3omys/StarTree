package com.example.startree

import com.example.startree.entity.DiseaseEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDateFromLong(timestamp: Long): String {
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(date)
}

var diseases = mutableListOf<DiseaseEntity>()