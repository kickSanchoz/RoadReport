package ru.roadreport.android.data.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.roadreport.android.data.local.models.DraftModelRoom

@Parcelize
data class DraftModel (
    val id: Int = 0,
    val title: String,
    val claim: ClaimModel,
) : Parcelable

fun DraftModel.toRoom(): DraftModelRoom {
    return DraftModelRoom(
        title = title,
        url = claim.url,
        latitude = claim.geoLocation.latitude,
        longitude = claim.geoLocation.longitude,
        datetime = System.currentTimeMillis()
    )
}
