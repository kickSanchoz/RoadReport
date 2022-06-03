package ru.roadreport.android.ui.claim

import androidx.fragment.app.viewModels
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseFragment
import ru.roadreport.android.databinding.FragmentClaimBinding
import ru.roadreport.android.ui.claim.dialogs.ClaimBottomSheet


class ClaimFragment : BaseFragment<FragmentClaimBinding>() {
    private val viewModel: ClaimViewModel by viewModels()

    private var claimBottomSheet: ClaimBottomSheet? = null

    override fun setLayoutId(): Int = R.layout.fragment_claim

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.YourClaims))
    }

    override fun observeViews() {
        binding.lCreateClaim.cwCardAppend.setOnClickListener {
            if (claimBottomSheet?.isVisible != true) {
                claimBottomSheet = ClaimBottomSheet()
                claimBottomSheet?.show(childFragmentManager, null)
            }
        }
    }
}