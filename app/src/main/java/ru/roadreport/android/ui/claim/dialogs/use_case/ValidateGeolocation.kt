package ru.roadreport.android.ui.claim.dialogs.use_case

import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.utils.use_case.IValidate
import ru.roadreport.android.utils.use_case.ValidationResult
import javax.inject.Inject

class ValidateGeolocation @Inject constructor(): IValidate<GeoLocation> {
    override fun execute(data: GeoLocation?): ValidationResult {
        if (data == null) {
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
