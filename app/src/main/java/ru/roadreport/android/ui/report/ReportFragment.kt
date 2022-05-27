package ru.roadreport.android.ui.report

import androidx.fragment.app.viewModels
import ru.one2work.android.customer.base.BaseFragment
import ru.roadreport.android.R
import ru.roadreport.android.databinding.FragmentReportBinding

class ReportFragment : BaseFragment<FragmentReportBinding>() {
    val viewModel: ReportViewModel by viewModels()

    override fun setLayoutID(): Int = R.layout.fragment_report

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.ReadyReports))
    }
}