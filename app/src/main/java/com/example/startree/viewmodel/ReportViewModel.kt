package com.example.startree.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.startree.entity.ReportEntity
import com.example.startree.repository.ReportRepository
import javax.inject.Inject

class ReportViewModel @Inject constructor(private val repository: ReportRepository) : ViewModel() {
    val reportList : MutableLiveData<ReportEntity> = MutableLiveData()

    // functions
}