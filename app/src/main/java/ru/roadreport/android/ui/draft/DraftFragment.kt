package ru.roadreport.android.ui.draft

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.roadreport.android.R
import ru.roadreport.android.base.BaseFragment
import ru.roadreport.android.data.domain.models.ClaimModel
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.data.domain.models.GeoLocation
import ru.roadreport.android.databinding.FragmentDraftBinding

@AndroidEntryPoint
class DraftFragment : BaseFragment<FragmentDraftBinding>(){
    private val viewModel: DraftViewModel by viewModels()

    private val draftAdapter by lazy {
        DraftAdapter(childFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.fragment_draft

    override fun setupViews() {
        setupAppBar(binding.appBar, getString(R.string.YourDrafts), R.drawable.ic_add)

        binding.appBar.btnAction.setOnClickListener {
            val newDraft = DraftModel(
                0,
                "Созданный черновик",
                ClaimModel(
                    url = "/storage/emulated/0/Android/data/ru.roadreport/files/Pictures/rr_07062022_1725331889721507041962856.jpg",
                    geoLocation = GeoLocation(
                        latitude = -99.999999,
                        longitude = -00.000000,
                    ),
                    datetime = System.currentTimeMillis().toString()
                )
            )
            viewModel.createDraft(newDraft)
        }

        binding.rvDrafts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvDrafts.adapter = draftAdapter
    }

    override fun observeData() {
        viewModel.draftList.observe(viewLifecycleOwner){
            draftAdapter.appendAll(it)
        }
    }

    override fun actionOnDestroyView() {
        binding.rvDrafts.adapter = null
    }
}