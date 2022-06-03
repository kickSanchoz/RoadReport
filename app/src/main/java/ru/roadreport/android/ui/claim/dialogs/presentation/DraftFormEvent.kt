package ru.roadreport.android.ui.claim.dialogs.presentation

import ru.roadreport.android.data.domain.models.GeoLocation
import java.io.File

sealed class DraftFormEvent {
    data class PhotoFileChanged(val photoFile: File?): DraftFormEvent()
    data class GeolocationChanged(val geolocation: GeoLocation?): DraftFormEvent()
    data class TitleChanged(val title: String?): DraftFormEvent()

    object Submit: DraftFormEvent()
}
