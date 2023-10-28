package com.example.startree.repository

import com.example.startree.dao.DiseaseDao
import javax.inject.Inject

class DiseaseRepository @Inject constructor(private val diseaseDao: DiseaseDao) {

}