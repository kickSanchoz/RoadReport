package ru.roadreport.android.data.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.roadreport.android.R

@Parcelize
data class ClaimModel(
    val id: Int? = null,
    val url: String? = null,
    val geoLocation: GeoLocation,
    val datetime: String,
    val receive_status: Int = 0
) : Parcelable {
    val status: ClaimStatus = ClaimStatus.fromInt(receive_status)
}

enum class ClaimStatus(val value: Int){
    DRAFT(0) {
        override val stringId: Int
            get() = R.string.StatusDraft
        override val colorId: Int
            get() = R.color.grey1
    },
    WAIT(1) {
        override val stringId: Int
            get() = R.string.StatusWaiting
        override val colorId: Int
            get() = R.color.orange1
    },
    APPROVED(2) {
        override val stringId: Int
            get() = R.string.StatusApproved
        override val colorId: Int
            get() = R.color.green2
    };

    abstract val stringId: Int
    abstract val colorId: Int

    companion object {
        fun fromInt(value: Int) = ClaimStatus.values().first { it.value == value }
    }
}
