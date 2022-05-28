package ru.roadreport.android.data.domain.models

import ru.roadreport.android.data.local.models.DraftModelRoom

data class DraftModel(
    val id: Int = 0,
    val title: String,
    val claim: ClaimModel,
)

fun DraftModel.toRoom(): DraftModelRoom {
    return DraftModelRoom(
        title = title,
        latitude = claim.latitude,
        longitude = claim.longitude,
        datetime = System.currentTimeMillis()
    )
}
