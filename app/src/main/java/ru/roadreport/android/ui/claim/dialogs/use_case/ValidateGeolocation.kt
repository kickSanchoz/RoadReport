package ru.roadreport.android.ui.claim.dialogs.use_case

import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.utils.use_case.ValidationResult
import javax.inject.Inject

class ValidateGeolocation @Inject constructor() {
    fun execute(geolocation: GeoLocation?): ValidationResult {
        if (geolocation == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "possible TODO in future"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
