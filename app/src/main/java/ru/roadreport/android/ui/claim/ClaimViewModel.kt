package ru.roadreport.android.ui.claim

import androidx.lifecycle.ViewModel
import ru.roadreport.android.data.domain.models.GeoLocation
import java.io.File

class ClaimViewModel : ViewModel() {
    var file: File? = null
    var geoLocation: GeoLocation? = null
}