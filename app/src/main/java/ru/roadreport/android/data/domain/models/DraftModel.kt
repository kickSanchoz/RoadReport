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
        url = claim.url,
        latitude = claim.geoLocation.latitude,
        longitude = claim.geoLocation.longitude,
        datetime = System.currentTimeMillis()
    )
}
