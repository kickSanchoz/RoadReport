package ru.roadreport.android.ui.claim.dialogs.use_case

import ru.roadreport.android.MainApplication
import ru.roadreport.android.R
import ru.roadreport.android.utils.use_case.IValidate
import ru.roadreport.android.utils.use_case.ValidationResult
import javax.inject.Inject

class ValidateTitle @Inject constructor(): IValidate<String> {
    override fun execute(data: String?): ValidationResult {
        if (data.isNullOrBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = MainApplication.res.getString(R.string.TitleDraftRequired)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}