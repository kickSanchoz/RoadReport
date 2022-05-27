package ru.roadreport.android.ui.draft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.roadreport.android.data.domain.ClaimModel
import ru.roadreport.android.data.domain.DraftModel
import ru.roadreport.android.utils.toAppDate

class DraftViewModel : ViewModel() {
    val draftList = MutableLiveData<List<DraftModel>>().apply {
        value = listOf(
            DraftModel(
                1,
                "Черновик 1",
                ClaimModel(
                    1,
                    12.121212,
                    21.212121,
//                    "15.03.2021 12:34"
                    toAppDate( 1647336840000),
                ),
            ),
            DraftModel(
                2,
                "Черновик 2",
                ClaimModel(
                    2,
                    34.343434,
                    43.434343,
                    "14.03.2021 23:45"
                ),
            ),
            DraftModel(
                3,
                "Черновик 3",
                ClaimModel(
                    3,
                    56.565656,
                    65.656565,
                    "13.03.2021 01:23"
                ),
            ),
        )
    }
}