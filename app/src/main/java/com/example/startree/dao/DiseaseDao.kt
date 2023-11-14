package com.example.startree.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.startree.entity.DiseaseEntity

@Dao
interface DiseaseDao {
    /*@Query("SELECT * FROM disease ORDER BY diseaseCode ASC")
    fun getAll(): List<DiseaseEntity>*/

    @Query("SELECT * FROM disease WHERE diseaseCode = :diseaseCode")
    fun getDiseaseByCode(diseaseCode: Int): DiseaseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<DiseaseEntity>)

    /*@Insert
    fun insertAll(vararg users: User)*/

    /*@Delete
    suspend fun deleteDisease(diseaseEntity: DiseaseEntity)*/
}