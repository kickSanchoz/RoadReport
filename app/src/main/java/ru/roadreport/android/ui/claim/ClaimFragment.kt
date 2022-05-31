package ru.roadreport.android.ui.claim

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseFragment
import ru.roadreport.android.databinding.FragmentClaimBinding
import ru.roadreport.android.utils.ISelectPicture
import ru.roadreport.android.utils.PictureHandler


class ClaimFragment : BaseFragment<FragmentClaimBinding>() {
    val viewModel: ClaimViewModel by viewModels()
    private lateinit var qwe: ISelectPicture

    override fun setLayoutId(): Int = R.layout.fragment_claim

    override fun setupActivityResults() {
        qwe = PictureHandler(this)
    }

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.YourClaims))
    }

    private fun takePicture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1)
        }
    }

    override fun observeViews() {
        binding.lCreateClaim.cwCardAppend.setOnClickListener {
//            selectPictureLauncher.launch("image/*")

            qwe.showPicker {
                Log.e("path", "${it?.absoluteFile}")
                if (it == null) {
                    Log.e("File", "not found")
                }
                else {
                    binding.tmpImageView.setImageURI(it.toUri())
                }
            }
        }
    }
}