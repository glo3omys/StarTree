package com.example.startree

import android.content.Context
import androidx.room.Room
import com.example.startree.dao.DiseaseDao
import com.example.startree.dao.ReportDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MyModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "appDatabase"
        ).build()
    }

    @Provides
    fun provideDiseaseDao(db: AppDatabase): DiseaseDao = db.diseaseDao()

    @Provides
    fun provideReportDao(db: AppDatabase): ReportDao = db.reportDao()
}