package ru.roadreport.android.utils.gallery

import android.view.ViewGroup
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseDialogFragment
import ru.roadreport.android.databinding.DialogGalleryBinding
import java.io.File

class GalleryDialogFragment(private val photoFileList: List<File>): BaseDialogFragment<DialogGalleryBinding>() {
    private val galleryAdapter by lazy {
        GalleryAdapter()
    }

    override fun getLayoutId(): Int = R.layout.dialog_gallery

    override fun getDialogHeight(): Int = ViewGroup.LayoutParams.MATCH_PARENT

    override fun getTheme(): Int = R.style.GalleryDialogTheme

    override fun getWindowAnimation(): Int = R.style.DialogAnimationExit

    override fun setupViews() {
        binding.viewPagerGallery.adapter = galleryAdapter
    }

    override fun observeViews() {
        binding.appBar.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun observeData() {
        galleryAdapter.appendAll(photoFileList)
    }
}