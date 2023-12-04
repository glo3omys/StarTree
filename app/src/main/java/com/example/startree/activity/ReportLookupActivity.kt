package com.example.startree.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.startree.R
import com.example.startree.databinding.ActivityReportLookupBinding
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

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