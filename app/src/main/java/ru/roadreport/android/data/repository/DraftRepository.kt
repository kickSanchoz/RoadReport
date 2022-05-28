package ru.roadreport.android.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.data.domain.models.toRoom
import ru.roadreport.android.data.local.dao.DraftDAO
import ru.roadreport.android.data.local.models.toDomain
import javax.inject.Inject

class DraftRepository @Inject constructor(
    private val draftDAO: DraftDAO,
) {

    fun getDrafts(): LiveData<List<DraftModel>> {
        return draftDAO.getDrafts().map { it.map { d -> d.toDomain() } }
    }

    suspend fun createDraft(draftModel: DraftModel) {
        withContext(Dispatchers.IO) {
            draftDAO.insertDraft(draftModel.toRoom())
        }
    }

    suspend fun deleteDraft(draftModel: DraftModel) {
        withContext(Dispatchers.IO) {
            draftDAO.deleteDraft(draftModel.toRoom())
        }
    }

    suspend fun deleteDraftById(draftId: Int) {
        withContext(Dispatchers.IO) {
            draftDAO.deleteDraftById(draftId)
        }
    }

}