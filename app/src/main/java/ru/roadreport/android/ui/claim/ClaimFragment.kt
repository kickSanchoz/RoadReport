package ru.roadreport.android.ui.claim

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

    override fun observeViews() {
        binding.lCreateClaim.cwCardAppend.setOnClickListener {
            if (!claimBottomSheet.dialogVisible){
                claimBottomSheet.show(childFragmentManager, null)
            }
        }
    }
}