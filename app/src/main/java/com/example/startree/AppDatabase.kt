package com.example.startree

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.startree.dao.DiseaseDao
import com.example.startree.dao.ReportDao
import com.example.startree.entity.DiseaseEntity
import com.example.startree.entity.ReportEntity

@Database(entities = [ReportEntity::class, DiseaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reportDao() : ReportDao
    abstract fun diseaseDao() : DiseaseDao


}