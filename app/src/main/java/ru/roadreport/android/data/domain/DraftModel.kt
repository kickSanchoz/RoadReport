package ru.roadreport.android.data.domain

import ru.roadreport.android.data.local.DraftModelRoom

data class DraftModel(
    val id: Int,
    val title: String,
    val claim: ClaimModel,
)

fun DraftModel.toRoom(): DraftModelRoom {
    return DraftModelRoom(
        id = 0,
        title = title,
        latitude = claim.latitude,
        longitude = claim.longitude,
        datetime = System.currentTimeMillis()
    )
}
