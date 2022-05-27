package ru.roadreport.android.ui.claim

import androidx.fragment.app.viewModels
import ru.one2work.android.customer.base.BaseFragment
import ru.roadreport.android.R
import ru.roadreport.android.databinding.FragmentClaimBinding

class ClaimFragment : BaseFragment<FragmentClaimBinding>() {
    val viewModel: ClaimViewModel by viewModels()

    override fun setLayoutID(): Int = R.layout.fragment_claim

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.YourClaims))
    }
}