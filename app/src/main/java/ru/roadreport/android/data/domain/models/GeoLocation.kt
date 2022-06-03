package ru.roadreport.android.data.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeoLocation(
    val latitude: Double,
    val longitude: Double,
) : Parcelable
