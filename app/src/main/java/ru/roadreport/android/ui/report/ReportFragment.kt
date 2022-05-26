package ru.roadreport.android.ui.report

import ru.one2work.android.customer.base.BaseFragment
import ru.roadreport.android.R
import ru.roadreport.android.databinding.FragmentReportBinding

class ReportFragment : BaseFragment<FragmentReportBinding>() {
    override fun setLayoutID(): Int = R.layout.fragment_report

    override fun setupViews() {
        setupAppBarSearch(binding.appBar, getString(R.string.YourDrafts))


    }
}