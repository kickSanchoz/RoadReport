package ru.roadreport.android.ui.claim.dialogs.presentation

import androidx.lifecycle.MutableLiveData
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidateTitle
import ru.roadreport.android.utils.use_case.ValidationResult
import javax.inject.Inject

class TitleResult @Inject constructor(
    private val validateTitle: ValidateTitle
) {
    var title = MutableLiveData<String?>().apply {
        observeForever {
            validationResult.value = validateTitle.execute(it)
        }
    }

    var validationResult = MutableLiveData<ValidationResult>()
}