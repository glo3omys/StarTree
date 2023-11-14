package com.example.startree.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.startree.R
import com.example.startree.Report
import com.example.startree.entity.ReportEntity

class ReportLookupAdapter(private val context: Context)
    : RecyclerView.Adapter<ReportLookupAdapter.ViewHolder>()
{
    // var reports : List<Report> = emptyList()
    var reports: List<ReportEntity> = emptyList()

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

        // private val diseaseDao = DiseaseDao()
        fun bind(item: ReportEntity, position: Int) {
            tvReportId.text = item.reportId.toString()
            tvReportDate.text = item.reportDate.toString()
            tvReportName.text = "report name"
            // tvReportName.text = item.diseaseName

            itemView.setOnClickListener {
                val nextIntent = Intent(context, ReportDetailActivity::class.java)
                // nextIntent.putExtra("report", reports[position])
                nextIntent.putExtra("diseaseCode", reports[position].diseaseCode)
                nextIntent.putExtra("reportId", reports[position].reportId)
                context.startActivity(nextIntent)
            }
        }
    }

    /*private fun combineReport(reports: List<ReportEntity>): List<Report> {
        return reports.map { report ->
            val diseaseData : DiseaseEntity = diseaseDao.getDiseaseByCode(report.diseaseCode)
            Report(reportId = report.reportId, reportDate = report.reportDate, imageUri = report.imageUri, diseaseCode = report.diseaseCode, diseaseName = diseaseData.diseaseName, diseaseExplain = diseaseData.diseaseExplain, diseaseSolution = diseaseData.diseaseSolution)
        }

        *//*val newReports : List<Report> = emptyList()
        reports.forEach { it->
            newReports.add(Report(it.reportId))
        }*//*
    }*/

    fun updateReports(updatedReports: List<ReportEntity>) {
        // reports = combineReport(updatedReports)
        reports = updatedReports
        notifyDataSetChanged()
    }
}