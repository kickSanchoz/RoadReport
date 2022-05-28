package ru.roadreport.android.ui.draft.dialogs

import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseBottomSheet
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.databinding.BottomSheetDraftBinding
import ru.roadreport.android.ui.draft.DraftViewModel

interface IDraftBottomSheet {
    fun show(draftModel: DraftModel, manager: FragmentManager, tag: String?)
    val dialogVisible: Boolean

    companion object {
//        fun create(draftDeleteListener: IDraftDelete): DraftBottomSheet =
//            DraftBottomSheet(draftDeleteListener)
        fun create(): IDraftBottomSheet = DraftBottomSheet()
    }
}

@AndroidEntryPoint
class DraftBottomSheet : BaseBottomSheet<BottomSheetDraftBinding>(), IDraftBottomSheet {
    private val viewModel: DraftViewModel by activityViewModels()

    private var draft: DraftModel? = null

    override fun setLayoutId(): Int = R.layout.bottom_sheet_draft

    override fun show(draftModel: DraftModel, manager: FragmentManager, tag: String?) {
        draft = draftModel
        super.show(manager, tag)
    }

    override val dialogVisible: Boolean
        get() = super.isVisible()

    override fun setupViews() {
        binding.apply {
            draft?.let {
                tvTitle.text = it.title
                tvDatetime.text = it.claim.datetime
                tvLatitude.text = it.claim.latitude.toString()
                tvLongitude.text = it.claim.longitude.toString()
                btnImgSrc = ResourcesCompat.getDrawable(resources, R.drawable.ic_logo, null)
            }
        }
    }

    override fun observeViews() {
        binding.btnDelete.setOnClickListener {
            Log.e("draft", "$draft")
            draft?.let {
                viewModel.deleteDraftById(it.id)
            }
            dismiss()
        }
    }
}