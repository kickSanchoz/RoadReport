package ru.roadreport.android.data.domain

import ru.roadreport.android.utils.CLAIM_LOCAL_STATUS

data class ClaimModel(
    val id: Int? = null,
    val latitude: Double,
    val longitude: Double,
    val datetime: String,
    val status: String = CLAIM_LOCAL_STATUS
)
