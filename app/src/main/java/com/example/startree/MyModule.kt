package com.example.startree

import android.content.Context
import androidx.room.Room
import com.example.startree.dao.DiseaseDao
import com.example.startree.dao.ReportDao
import com.example.startree.repository.DiseaseRepository
import com.example.startree.repository.ReportRepository
import com.example.startree.viewmodel.ReportViewModel
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

    @Provides
    fun provideInitializeDatabase(@ApplicationContext context: Context): InitializeDatabase {
        return InitializeDatabase(context)
    }

    @Provides
    fun provideReportViewModel(reportRepository: ReportRepository, diseaseRepository: DiseaseRepository): ReportViewModel {
        return ReportViewModel(reportRepository, diseaseRepository)
    }
}