package ru.roadreport.android.ui.draft.dialogs

import androidx.fragment.app.FragmentManager
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseBottomSheet
import ru.roadreport.android.databinding.BottomSheetDraftBinding

interface DraftBottomSheet {
    fun show(id: Int, manager: FragmentManager, tag: String?)
    val dialogVisible: Boolean

    companion object {
        fun create(): DraftBottomSheet = DraftBottomSheetImpl()
    }
}

class DraftBottomSheetImpl : BaseBottomSheet<BottomSheetDraftBinding>(), DraftBottomSheet {
    override fun setLayoutId(): Int = R.layout.bottom_sheet_draft

    override fun show(id: Int, manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    override val dialogVisible: Boolean
        get() = super.isVisible()
}