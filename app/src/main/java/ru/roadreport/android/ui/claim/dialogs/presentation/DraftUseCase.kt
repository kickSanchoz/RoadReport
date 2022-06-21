package ru.roadreport.android.ui.claim.dialogs.presentation

import androidx.lifecycle.MutableLiveData
import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidateGeolocation
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidatePhotoFile
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidateTitle
import ru.roadreport.android.utils.use_case.ValidationResult
import java.io.File
import javax.inject.Inject

class DraftUseCase @Inject constructor(
    private val validatePhotoFile: ValidatePhotoFile,
    private val validateGeolocation: ValidateGeolocation,
    private val validateTitle: ValidateTitle,
) {
    var photoFile = MutableLiveData<File>().apply {
        observeForever {
            photoFileError.value = validatePhotoFile.execute(it)
        }
    }
    var photoFileError = MutableLiveData<ValidationResult>()

    var geoLocation = MutableLiveData<GeoLocation>().apply {
        observeForever {
            geoLocationError.value = validateGeolocation.execute(it)
        }
    }
    var geoLocationError = MutableLiveData<ValidationResult>()


    var title = MutableLiveData<String>().apply {
        observeForever {
            titleError.value = validateTitle.execute(it)
        }
    }
    var titleError = MutableLiveData<ValidationResult>()
}