package ru.roadreport.android.ui.claim.dialogs

import android.util.Log
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseBottomSheet
import ru.roadreport.android.databinding.BottomSheetClaimBinding
import ru.roadreport.android.ui.claim.ClaimViewModel
import ru.roadreport.android.utils.ILocation
import ru.roadreport.android.utils.ISelectPicture
import ru.roadreport.android.utils.LocationHandler
import ru.roadreport.android.utils.PictureHandler

interface IClaimBottomSheet {
    fun show(manager: FragmentManager, tag: String?)
    val dialogVisible: Boolean

    companion object {
        fun create(): IClaimBottomSheet = ClaimBottomSheet()
    }
}

class ClaimBottomSheet : BaseBottomSheet<BottomSheetClaimBinding>(), IClaimBottomSheet {
    private val viewModel: ClaimViewModel by viewModels()

    private lateinit var pictureSelector: ISelectPicture
    private lateinit var locationHandler: ILocation

    override fun setLayoutId(): Int = R.layout.bottom_sheet_claim

    override fun setupActivityResults() {
        pictureSelector = PictureHandler(this)
        locationHandler = LocationHandler(this)
    }

    override fun setupViews() {
        binding.lAttachPhoto.cwCardAppend.setOnClickListener {
            getLocation()
        }
        binding.ivPicture.setOnClickListener {
            getLocation()
        }
    }

    private fun getLocation() {
        locationHandler.locationListener {
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
            Log.e("absolute file path", "${file?.absoluteFile}")
            if (file == null) {
                viewModel.file = null
                pictureNotFound()
                Log.e("File", "not found")
            }
            else {
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

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    override val dialogVisible: Boolean = super.isVisible()
}