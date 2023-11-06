package com.example.startree.repository

import com.example.startree.dao.DiseaseDao
import com.example.startree.entity.DiseaseEntity
import javax.inject.Inject

class DiseaseRepository @Inject constructor(private val diseaseDao: DiseaseDao) {

    fun getDiseaseById(diseaseId: Int): DiseaseEntity {
        return diseaseDao.getDiseaseById(diseaseId)
    }

}