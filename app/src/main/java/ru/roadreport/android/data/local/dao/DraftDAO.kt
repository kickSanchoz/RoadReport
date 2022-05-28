package ru.roadreport.android.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.roadreport.android.data.local.models.DraftModelRoom

@Dao
interface DraftDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDraft(draft: DraftModelRoom): Long

    @Query ("SELECT * FROM draft ORDER BY datetime")
    fun getDrafts(): LiveData<List<DraftModelRoom>>

    @Delete
    fun deleteDraft(draft: DraftModelRoom)

    @Query ("DELETE FROM draft WHERE id = :draftId")
    fun deleteDraftById(draftId: Int)
}