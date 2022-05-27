package ru.roadreport.android.ui.draft

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.roadreport.android.data.domain.DraftModel
import ru.roadreport.android.databinding.ItemDraftBinding
import ru.roadreport.android.ui.draft.dialogs.DraftBottomSheet

class DraftAdapter(val fragmentManager: IFragmentManager) : RecyclerView.Adapter<DraftAdapter.DraftViewHolder>() {
    private val draftsList = mutableListOf<DraftModel>()

    private val bottomSheet by lazy {
        DraftBottomSheet.create()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DraftViewHolder {
        val view = ItemDraftBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DraftViewHolder(view)
    }

    override fun onBindViewHolder(holder: DraftViewHolder, position: Int) {
        holder.bind(draftsList[position])
    }

    override fun getItemCount(): Int {
        return draftsList.size
    }

    private fun clear() {
        draftsList.clear()
    }

    fun addAll(drafts: List<DraftModel>) {
        clear()
        draftsList.addAll(drafts)
    }

    inner class DraftViewHolder(val binding: ItemDraftBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(draft: DraftModel) {
            binding.tvDraftTitle.text = draft.title
            binding.tvLatitude.text = draft.claim.latitude.toString()
            binding.tvLongitude.text = draft.claim.longitude.toString()
            binding.tvDatetime.text = draft.claim.datetime

            binding.root.setOnClickListener {
                if (!bottomSheet.dialogVisible) {
                    bottomSheet.show(1, fragmentManager.localFragmentManager, null)
                }
            }
        }
    }
}