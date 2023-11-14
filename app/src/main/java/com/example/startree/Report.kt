package com.example.startree

import android.os.Parcel
import android.os.Parcelable

data class Report(
    val reportId: Int?,
    val reportDate: String, // Long
    val imageUri: String,
    val diseaseCode: Int,
    val diseaseName: String,
    val diseaseExplain: String,
    val diseaseSolution: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(reportId)
        parcel.writeString(reportDate)
        parcel.writeString(imageUri)
        parcel.writeInt(diseaseCode)
        parcel.writeString(diseaseName)
        parcel.writeString(diseaseExplain)
        parcel.writeString(diseaseSolution)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Report> {
        override fun createFromParcel(parcel: Parcel): Report {
            return Report(parcel)
        }

        override fun newArray(size: Int): Array<Report?> {
            return arrayOfNulls(size)
        }
    }
}