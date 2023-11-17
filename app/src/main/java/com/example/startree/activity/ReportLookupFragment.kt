package com.example.startree.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.startree.R
import com.example.startree.databinding.FragmentReportLookupBinding
import com.example.startree.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportLookupFragment : Fragment() {
    lateinit var mContext : Context
    lateinit var reportLookupAdapter: ReportLookupAdapter

    private var mBinding: FragmentReportLookupBinding? = null
    private val binding get() = mBinding!!

    private val reportViewModel: ReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = this.requireContext()
        reportLookupAdapter = ReportLookupAdapter(mContext) { report ->
            val reportDetailFragment = ReportDetailFragment()

            val bundle = Bundle()
            bundle.putParcelable("report", report)

            reportDetailFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_report, reportDetailFragment)
                .addToBackStack("FragmentReportDetail")
                .commit()
        }
        mBinding = FragmentReportLookupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportViewModel.reports.observe(viewLifecycleOwner) { reports ->
            lifecycleScope.launch {
                reportLookupAdapter.updateReports(reports)
            }
        }

        binding.rvReportsList.adapter = reportLookupAdapter

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back button press
                requireActivity().finish()
            }
        })
    }

}