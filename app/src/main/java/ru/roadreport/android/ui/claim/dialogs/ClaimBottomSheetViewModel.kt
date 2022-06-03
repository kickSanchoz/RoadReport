package ru.roadreport.android.ui.claim.dialogs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.ui.claim.dialogs.presentation.DraftFormEvent
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidateGeolocation
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidatePhotoFile
import ru.roadreport.android.ui.claim.dialogs.use_case.ValidateTitle
import ru.roadreport.android.utils.use_case.ValidationResult
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ClaimBottomSheetViewModel @Inject constructor(
    private val validatePhotoFile: ValidatePhotoFile,
    private val validateGeolocation: ValidateGeolocation,
    private val validateTitle: ValidateTitle,
) : ViewModel() {
    var file: File? = null
    var geoLocation: GeoLocation? = null

    var isDraftMode: Boolean = false

    val photoFileData = MutableLiveData<ValidationResult>()
    val geolocationData = MutableLiveData<ValidationResult>()
    val titleData = MutableLiveData<ValidationResult>()

    fun onEvent(event: DraftFormEvent) {
        when(event){
            is DraftFormEvent.PhotoFileChanged -> {
                photoFileData.value = validatePhotoFile.execute(event.photoFile)
            }
            is DraftFormEvent.GeolocationChanged -> {
                geolocationData.value = validateGeolocation.execute(event.geolocation)
            }
            is DraftFormEvent.TitleChanged -> {
                titleData.value = validateTitle.execute(event.title)
            }
            is DraftFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun hasError(): Boolean {
        //Инициализация значений в случае, если была нажата кнопка без изменения необходимых полей
        if (photoFileData.value == null) {
            photoFileData.value = validatePhotoFile.execute(null)
        }
        if (geolocationData.value == null) {
            geolocationData.value = validateGeolocation.execute(null)
        }
        if (isDraftMode && titleData.value == null) {
            titleData.value = validateTitle.execute(null)
        }

        return listOf(
            photoFileData.value,
            geolocationData.value,
            titleData.value
        ).any { !it!!.successful }
    }

    private fun submitData() {
        if (!hasError()) {
            Log.e("Pressed", "no has error")
        }
        else {
            Log.e("Pressed", "has error")
        }
    }
}