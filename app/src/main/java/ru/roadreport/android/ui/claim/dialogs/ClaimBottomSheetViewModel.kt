package ru.roadreport.android.ui.claim.dialogs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.ui.claim.dialogs.presentation.DraftFormEvent
import ru.roadreport.android.ui.claim.dialogs.presentation.DraftUseCase
import ru.roadreport.android.ui.claim.dialogs.presentation.GeolocationResult
import ru.roadreport.android.ui.claim.dialogs.presentation.TitleResult
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
    val geolocationResult: GeolocationResult, //TODO 1
    val titleResult: TitleResult, //TODO 2
    val draftUseCase: DraftUseCase, //TODO 3
) : ViewModel() {
    var file: File? = null
    var geoLocation: GeoLocation? = null

    var isDraftMode: Boolean = false

    val photoFileData = MutableLiveData<ValidationResult>()
    val geolocationData = MutableLiveData<ValidationResult>()
    val titleData = MutableLiveData<ValidationResult>()

//    val geolocationResult = MutableLiveData<GeolocationResult>()

    fun onEvent(event: DraftFormEvent) {
        when(event){
            is DraftFormEvent.PhotoFileChanged -> {
                photoFileData.value = validatePhotoFile.execute(event.photoFile)

                //~~~~~~~~~~~~~~~~~~~~~~~
                draftUseCase.photoFile.value = event.photoFile //TODO 3
            }
            is DraftFormEvent.GeolocationChanged -> {
                geolocationData.value = validateGeolocation.execute(event.geolocation)

                geolocationResult.geoLocation.value = event.geolocation //TODO 1
                //TODO протестить
                //1й вариант
                //geolocationResult.value = GeolocationResult(event.geolocation, validateGeolocation.execute(event.geolocation))
                //2й вариант
                //geolocationResult.value = GeolocationResult(event.geolocation)
            }
            is DraftFormEvent.TitleChanged -> {
                titleData.value = validateTitle.execute(event.title)

                titleResult.title.value = event.title //TODO 2
            }
            is DraftFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun hasError(): Boolean {
        //~~~~~~~~~~~~~~~~~~~~~~~~~~
        if (draftUseCase.photoFile.value == null) {
            Log.e("photoFileNull", "${draftUseCase.photoFile.value}") //TODO 3
        }

        //Инициализация значений в случае, если была нажата кнопка без изменения необходимых полей
        if (photoFileData.value == null) {
            photoFileData.value = validatePhotoFile.execute(null)
        }
        if (geolocationData.value == null) {
            geolocationData.value = validateGeolocation.execute(null)
        }
        //Слушатель на название добавляется, если включен режим добавления черновика
        if (isDraftMode && titleData.value == null) {
            titleData.value = validateTitle.execute(null)

            titleResult.validationResult.value = null //TODO 2
        }

        if (isDraftMode) {
            return listOf(
                photoFileData.value,
                geolocationData.value,
                titleData.value,
                titleResult.validationResult.value //TODO 2
            ).any { it?.successful != true}
        }
        else {
            return listOf(
                photoFileData.value,
                geolocationData.value
            ).any { it?.successful != true}
        }


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