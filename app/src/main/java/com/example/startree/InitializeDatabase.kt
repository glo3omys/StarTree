package com.example.startree

import android.content.Context
import androidx.room.Room
import com.example.startree.entity.DiseaseEntity
import com.google.gson.Gson

class InitializeDatabase(private val context: Context) {
    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "appDatabase").build()
    }

    fun parseAndInsertData() {
        val diseaseDao = appDatabase.diseaseDao()
        val rawResourceId = R.raw.diseasesjson
        val inputStream = context.resources.openRawResource(rawResourceId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val dataList = gson.fromJson(jsonString, Array<DiseaseEntity>::class.java).toList()

        diseaseDao.insertAll(dataList)
    }
}