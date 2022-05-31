package ru.roadreport.android.ui.claim

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseFragment
import ru.roadreport.android.databinding.FragmentClaimBinding
import ru.roadreport.android.ui.claim.dialogs.IClaimBottomSheet


class ClaimFragment : BaseFragment<FragmentClaimBinding>() {
    private val viewModel: ClaimViewModel by viewModels()

    private val claimBottomSheet by lazy {
        IClaimBottomSheet.create()
    }

    override fun setLayoutId(): Int = R.layout.fragment_claim

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
            if (!claimBottomSheet.dialogVisible){
                claimBottomSheet.show(childFragmentManager, null)
            }
        }
    }
}