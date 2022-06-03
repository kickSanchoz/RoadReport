package ru.roadreport.android.ui.draft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.data.repository.DraftRepository
import javax.inject.Inject

@HiltViewModel
class DraftViewModel @Inject constructor(
    private val draftRepository: DraftRepository,
) : ViewModel() {
    val draftList = draftRepository.getDrafts()

    var draft: DraftModel? = null

    fun createDraft(draft: DraftModel) {
        viewModelScope.launch {
            draftRepository.createDraft(draft)
        }
    }

//    fun deleteDraft(draft: DraftModel) {
//        viewModelScope.launch {
//            draftRepository.deleteDraft(draft)
//        }
//    }

    fun deleteDraftById(draftId: Int) {
        viewModelScope.launch {
            draftRepository.deleteDraftById(draftId)
        }
    }
}