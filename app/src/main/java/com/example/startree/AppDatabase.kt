package com.example.startree

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.startree.dao.DiseaseDao
import com.example.startree.dao.ReportDao
import com.example.startree.entity.DiseaseEntity
import com.example.startree.entity.ReportEntity

@Database(entities = [ReportEntity::class, DiseaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reportDao() : ReportDao
    abstract fun diseaseDao() : DiseaseDao

    // https://junyoung-developer.tistory.com/164
    // for singleton: companion object
    // access control: synchronized
    /*companion object {
        private var INSTANCE: AppDatabase ?= null

        fun getDatabase(context: Context): AppDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }*/

}