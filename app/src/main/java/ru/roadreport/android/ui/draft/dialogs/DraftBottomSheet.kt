package ru.roadreport.android.ui.draft.dialogs

import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseBottomSheet
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.databinding.BottomSheetDraftBinding
import ru.roadreport.android.ui.draft.DraftViewModel
import java.io.File
import java.io.FileNotFoundException

interface IDraftBottomSheet {
    fun show(draftModel: DraftModel, manager: FragmentManager, tag: String?)
    val dialogVisible: Boolean

    companion object {
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
                tvLatitude.text = it.claim.geoLocation.latitude.toString()
                tvLongitude.text = it.claim.geoLocation.longitude.toString()

                if (it.claim.url == null || !File(it.claim.url).exists()){
                    ivPicture.setImageDrawable(ResourcesCompat
                        .getDrawable(binding.root.resources, R.drawable.ic_photo_placeholder, null))
                }
                else {
                    try {
                        val file = File(it.claim.url)
                        val fileUri = file.toUri()
                        ivPicture.setImageURI(fileUri)
                    }
                    catch (e: FileNotFoundException) {
                        Log.e("FileUri", "$e")
                    }
                }
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