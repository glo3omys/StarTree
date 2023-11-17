package com.example.startree.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.startree.R
import com.example.startree.Report
import com.example.startree.dao.DiseaseDao
import com.example.startree.databinding.ActivityReportLookupBinding
import com.example.startree.entity.DiseaseEntity
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReportLookupActivity : AppCompatActivity() {
    private var mBinding: ActivityReportLookupBinding? = null
    private val binding get() = mBinding!!

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityReportLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_report, ReportLookupFragment())
                .addToBackStack("FragmentReportLookup")
                .commit()
        }
    }
}