package com.example.startree.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.startree.entity.DiseaseEntity

@Dao
interface DiseaseDao {
    /*@Query("SELECT * FROM disease ORDER BY diseaseCode ASC")
    fun getAll(): List<DiseaseEntity>*/

    @Query("SELECT * FROM disease WHERE diseaseCode = :diseaseCode")
    fun getDiseaseById(diseaseCode: Int): DiseaseEntity

    /*@Insert
    fun insertAll(vararg users: User)*/

    /*@Delete
    suspend fun deleteDisease(diseaseEntity: DiseaseEntity)*/
}