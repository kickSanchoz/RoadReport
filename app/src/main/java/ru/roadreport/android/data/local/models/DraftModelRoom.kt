package ru.roadreport.android.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.roadreport.android.data.domain.models.ClaimModel
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.utils.toAppDate

@Entity(tableName = "draft")
data class DraftModelRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val datetime: Long,
)

fun DraftModelRoom.toDomain(): DraftModel {
    return DraftModel(
        id = id,
        title = title,
        claim = ClaimModel(
            latitude = latitude,
            longitude = longitude,
            datetime = toAppDate(datetime),
        )
    )
}
