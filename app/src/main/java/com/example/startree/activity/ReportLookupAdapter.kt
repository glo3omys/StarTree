package com.example.startree.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.startree.R
import com.example.startree.Report
import com.example.startree.diseases
import com.example.startree.entity.ReportEntity
import com.example.startree.getDateFromLong

class ReportLookupAdapter(
    private val context: Context,
    private val clicked: (Report) -> Unit)
    : RecyclerView.Adapter<ReportLookupAdapter.ViewHolder>()
{
    var reports = mutableListOf<Report>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_reports, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = reports.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reports[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvReportId : TextView = itemView.findViewById(R.id.tv_report_id)
        private val tvReportDate : TextView = itemView.findViewById(R.id.tv_report_date)
        private val tvReportName : TextView = itemView.findViewById(R.id.tv_report_name)

        fun bind(item: Report, position: Int) {
            tvReportId.text = item.reportId.toString()
            tvReportDate.text = item.reportDate
            tvReportName.text = item.diseaseName

            itemView.setOnClickListener {
                clicked(reports[position])
            }
        }
    }

    private fun combineReport(reports: List<ReportEntity>): List<Report> {
        return reports.map { report ->
            val diseaseData = diseases.find { it.diseaseCode == report.diseaseCode } !!
            Report(reportId = report.reportId, reportDate = getDateFromLong(report.reportDate), imageUri = report.imageUri, diseaseCode = report.diseaseCode, diseaseName = diseaseData.diseaseName, diseaseExplain = diseaseData.diseaseExplain, diseaseSolution = diseaseData.diseaseSolution)
        }
    }

    fun updateReports(updatedReports: List<ReportEntity>) {
        reports = combineReport(updatedReports) as MutableList<Report>
        notifyDataSetChanged()
    }
}