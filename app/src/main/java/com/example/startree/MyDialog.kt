package com.example.startree

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.startree.R

class MyDialog(context: Context) {
    var mContext = context
    var mBuilder = AlertDialog.Builder(mContext)

    lateinit var mDialogView: View
    lateinit var mAlertDialog: AlertDialog

    fun deleteReport(delete: (Boolean) -> Unit) {
        mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete, null)

        mBuilder.setView(mDialogView)
            //.setTitle("confirm")
            //.setTitle(" ")
            .setCancelable(false)
        mAlertDialog = mBuilder.create()
        mAlertDialog.show()

        val cancelButton = mDialogView.findViewById<TextView>(R.id.custom_dialog_btn_cancel)
        val okButton = mDialogView.findViewById<TextView>(R.id.custom_dialog_btn_ok)

        cancelButton.setOnClickListener {
            mAlertDialog.dismiss()
            delete(false)
        }

        okButton.setOnClickListener {
            mAlertDialog.dismiss()
            delete(true)
        }
    }
}