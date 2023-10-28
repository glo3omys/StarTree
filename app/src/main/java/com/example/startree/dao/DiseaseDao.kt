package com.example.startree.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.startree.entity.DiseaseEntity

@Dao
interface DiseaseDao {
    @Query("SELECT * FROM disease")
    fun getAll(): List<DiseaseEntity>

    /*@Insert
    fun insertAll(vararg users: User)*/

    @Delete
    fun delete(diseaseEntity: DiseaseEntity)
}