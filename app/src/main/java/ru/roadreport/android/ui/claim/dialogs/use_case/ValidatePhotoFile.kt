package ru.roadreport.android.ui.claim.dialogs.use_case

import ru.roadreport.android.MainApplication
import ru.roadreport.android.R
import ru.roadreport.android.utils.use_case.ValidationResult
import java.io.File
import javax.inject.Inject

class ValidatePhotoFile @Inject constructor() {
    fun execute(photoFile: File?): ValidationResult {
        if (photoFile == null) {
            return ValidationResult(
                successful = false,
                errorMessage = MainApplication.res.getString(R.string.PhotoRequired)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}