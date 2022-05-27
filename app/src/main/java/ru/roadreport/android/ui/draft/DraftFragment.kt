package ru.roadreport.android.ui.draft

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.one2work.android.customer.base.BaseFragment
import ru.roadreport.android.R
import ru.roadreport.android.databinding.FragmentDraftBinding

interface IFragmentManager {
    val localFragmentManager: FragmentManager
}

class DraftFragment : BaseFragment<FragmentDraftBinding>(), IFragmentManager {
    private val viewModel: DraftViewModel by viewModels()

    private val draftAdapter by lazy {
        DraftAdapter(this)
    }

    override fun setLayoutID(): Int = R.layout.fragment_draft

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.YourDrafts))

        localFragmentManager = childFragmentManager

        binding.rvDrafts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvDrafts.adapter = draftAdapter
    }

    override fun observeData() {
        viewModel.draftList.observe(viewLifecycleOwner){
            draftAdapter.addAll(it)
        }
    }

    override lateinit var localFragmentManager: FragmentManager
}