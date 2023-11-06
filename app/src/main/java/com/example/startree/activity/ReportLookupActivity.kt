package com.example.startree.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.startree.R
import com.example.startree.databinding.ActivityReportLookupBinding

class ReportLookupActivity : AppCompatActivity() {
    private var mBinding: ActivityReportLookupBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityReportLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}