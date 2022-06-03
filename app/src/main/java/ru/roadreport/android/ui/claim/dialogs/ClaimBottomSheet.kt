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
import ru.roadreport.android.utils.ILocation
import ru.roadreport.android.utils.ISelectPicture
import ru.roadreport.android.utils.LocationHandler
import ru.roadreport.android.utils.PictureHandler

@AndroidEntryPoint
class ClaimBottomSheet : BaseBottomSheet<BottomSheetClaimBinding>() {
    private val viewModel: ClaimBottomSheetViewModel by viewModels()

    private lateinit var pictureSelector: ISelectPicture
    private lateinit var locationHandler: ILocation

    override fun getLayoutId(): Int = R.layout.bottom_sheet_claim

    override fun setLayoutHeight(): Int = LinearLayout.LayoutParams.MATCH_PARENT

    override fun setupActivityResults() {
        pictureSelector = PictureHandler(this)
        locationHandler = LocationHandler(this)
    }

    override fun observeViews() {
        binding.btnSend.setOnClickListener {
            setDraftMode()
//            viewModel.onEvent(DraftFormEvent.Create)
        }
        binding.lAttachPhoto.cwCardAppend.setOnClickListener {
            getLocation()
        }
        binding.ivPicture.setOnClickListener {
            getLocation()
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
        pictureSelector.showPicker { file ->
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
    }
}