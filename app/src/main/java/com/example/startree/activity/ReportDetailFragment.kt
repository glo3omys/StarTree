package com.example.startree.activity

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.startree.MyDialog
import com.example.startree.Report
import com.example.startree.databinding.FragmentReportDetailBinding
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportDetailFragment : Fragment() {
    lateinit var mContext : Context

    private var mBinding: FragmentReportDetailBinding? = null
    private val binding get() = mBinding!!

    private val reportViewModel: ReportViewModel by activityViewModels()

    private var report: Report? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = this.requireContext()
        mBinding = FragmentReportDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        report = arguments?.getParcelable("report")

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this@ReportDetailFragment)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            deleteReport()
        }

        initViews()
    }

    private fun initViews() {
        binding.imvPlant.setImageURI(Uri.parse(report?.imageUri))
        binding.imvPlant.setBackgroundColor(Color.TRANSPARENT)
        binding.tvDiseaseName.text = report?.diseaseName
        binding.tvDiseaseExplain.text = report?.diseaseExplain
        binding.tvDiseaseSolution.text = report?.diseaseSolution
    }

    private fun deleteReport() {
        val mDialog = MyDialog(requireActivity())
        mDialog.deleteReport { delete ->
            if (delete) {
                lifecycleScope.launch {
                    reportViewModel.deleteReportById(report?.reportId!!)

                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .remove(this@ReportDetailFragment)
                        .commit()
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }
}