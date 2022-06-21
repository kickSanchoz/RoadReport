package ru.roadreport.android.ui.report

import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import coil.load
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseFragment
import ru.roadreport.android.databinding.FragmentReportBinding
import java.io.File

class ReportFragment : BaseFragment<FragmentReportBinding>() {
    private val viewModel: ReportViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_report

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.ReadyReports))
    }

    override fun observeData() {
        val file = File("/storage/emulated/0/Android/data/ru.roadreport/files/Pictures/rr_03062022_221440360495174610414939.jpg")
        if (file.exists()){
            binding.pvPhoto.load("/storage/emulated/0/Android/data/ru.roadreport/files/Pictures/rr_03062022_221440360495174610414939.jpg".toUri())
        }
        else{
            Log.e("File", "not exist")
        }
    }
}