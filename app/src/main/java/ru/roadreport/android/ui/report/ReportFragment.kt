package ru.roadreport.android.ui.report

import androidx.fragment.app.viewModels
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseFragment
import ru.roadreport.android.databinding.FragmentReportBinding

class ReportFragment : BaseFragment<FragmentReportBinding>() {
    val viewModel: ReportViewModel by viewModels()

    override fun setLayoutId(): Int = R.layout.fragment_report

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.ReadyReports))
    }
}