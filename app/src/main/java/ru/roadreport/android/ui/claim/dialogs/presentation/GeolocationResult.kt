package ru.roadreport.android.ui.claim.dialogs.presentation

import androidx.lifecycle.MutableLiveData
import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidateGeolocation
import ru.roadreport.android.utils.use_case.ValidationResult
import javax.inject.Inject

class GeolocationResult @Inject constructor(
    private val validateGeolocation: ValidateGeolocation,
) {
    var geoLocation = MutableLiveData<GeoLocation?>().apply {
        observeForever {
            validationResult.value = validateGeolocation.execute(it)
        }
    }
//    var validationResult = Transformations.map(geoLocation){
//        validateGeolocation.execute(it)
//    }
    var validationResult = MutableLiveData<ValidationResult>()

}