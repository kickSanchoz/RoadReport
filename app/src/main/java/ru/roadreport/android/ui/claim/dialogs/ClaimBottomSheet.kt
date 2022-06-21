package ru.roadreport.android.ui.claim.dialogs

import android.util.Log
import android.widget.LinearLayout
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseBottomSheet
import ru.roadreport.android.databinding.BottomSheetClaimBinding
import ru.roadreport.android.ui.claim.dialogs.presentation.DraftFormEvent
import ru.roadreport.android.utils.ILocationHandler
import ru.roadreport.android.utils.IPictureHandler
import ru.roadreport.android.utils.LocationHandler
import ru.roadreport.android.utils.PictureHandler
import ru.roadreport.android.utils.gallery.GalleryDialogFragment
import java.io.File

@AndroidEntryPoint
class ClaimBottomSheet : BaseBottomSheet<BottomSheetClaimBinding>() {
    private val viewModel: ClaimBottomSheetViewModel by viewModels()

    private lateinit var pictureHandler: IPictureHandler
    private lateinit var locationHandler: ILocationHandler

    override fun getLayoutId(): Int = R.layout.bottom_sheet_claim

    override fun getLayoutHeight(): Int = LinearLayout.LayoutParams.MATCH_PARENT

    override fun setupActivityResults() {
        pictureHandler = PictureHandler(this)
        locationHandler = LocationHandler(this)
    }

    override fun observeViews() {
        binding.btnSend.setOnClickListener {
//            viewModel.onEvent(DraftFormEvent.Submit)
            setDraftMode()
        }
        binding.lAttachPhoto.cwCardAppend.setOnClickListener {
            getLocation()
        }
        binding.ivPicture.setOnClickListener {
//            getLocation()
            val photos = listOf(
                viewModel.file ?: File("/storage/emulated/0/Android/data/ru.roadreport/files/Pictures/rr_01062022_0117368024713007059008393.jpg"),
                File("/storage/emulated/0/Android/data/ru.roadreport/files/Pictures/rr_03062022_221440360495174610414939.jpg")
            )
            GalleryDialogFragment(photos).show(childFragmentManager, null)
        }

        binding.etAddTitle.addTextChangedListener {
            viewModel.onEvent(DraftFormEvent.TitleChanged(it.toString()))
        }
    }

    private fun setDraftMode() {
        viewModel.isDraftMode = true

        binding.btnSend.isVisible = false
        binding.btnCreate.isVisible = true

        binding.clTitleContent.isVisible = true

        binding.btnCreate.setOnClickListener {
            viewModel.onEvent(DraftFormEvent.Submit)
        }
    }

    private fun getLocation() {
        locationHandler.locationListener {
            viewModel.onEvent(DraftFormEvent.GeolocationChanged(it))
            if (it == null){
                //Перехватить пустую геолокацию, если этого не было сделано в классе
                Log.e("GeoLocation", "empty")
            }
            else {
                viewModel.geoLocation = it

                getPicture()
            }
        }
    }

    private fun getPicture() {
        pictureHandler.showPicker { file ->
            viewModel.onEvent(DraftFormEvent.PhotoFileChanged(file))
            Log.e("absolute file path", "${file?.absoluteFile}")
            if (file == null) {
                Log.e("File", "not found")
                viewModel.file = null
                pictureNotFound()
            }
            else {
                Log.e("absolute file path", "${file.absoluteFile}")
                viewModel.file = file
                binding.ivPicture.setImageURI(file.toUri())
                pictureFound()
            }
        }
    }

    private fun pictureFound() {
        binding.lAttachPhoto.root.isVisible = false
        binding.ivPicture.isVisible = true
    }

    private fun pictureNotFound() {
        binding.lAttachPhoto.root.isVisible = true
        binding.ivPicture.isVisible = false
    }

    override fun observeData() {
        viewModel.photoFileData.observe(viewLifecycleOwner) {
            Log.e("photoFileData.observe", "check in")
            if (!it.successful) {
                binding.tvAttachPhotoError.isVisible = true
                binding.tvAttachPhotoError.text = it.errorMessage
            }
            else {
                binding.tvAttachPhotoError.isVisible = false
            }
        }

        viewModel.geolocationData.observe(viewLifecycleOwner) {
            Log.e("geolocationData.observe", "check in")
        }

        viewModel.titleData.observe(viewLifecycleOwner) {
            Log.e("titleData.observe", "check in")
            if (!it.successful) {
                binding.tilAddTitle.isErrorEnabled = true
                binding.tilAddTitle.error = it.errorMessage
            }
            else {
                binding.tilAddTitle.isErrorEnabled = false
                binding.tilAddTitle.error = null
            }
        }


        viewModel.geolocationResult.validationResult.observe(viewLifecycleOwner) { //TODO 1
            Log.e("geolocationResult", "$it")
        }

        viewModel.titleResult.validationResult.observe(viewLifecycleOwner) { //TODO 2
            Log.e("titleResult", "$it")
        }

        viewModel.draftUseCase.photoFileError.observe(viewLifecycleOwner) {

        }
    }
}