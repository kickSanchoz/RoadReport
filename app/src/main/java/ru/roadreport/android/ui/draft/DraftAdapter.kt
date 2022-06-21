package ru.roadreport.android.ui.draft

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.roadreport.android.R
import ru.roadreport.android.data.domain.models.DraftModel
import ru.roadreport.android.databinding.ItemDraftBinding
import ru.roadreport.android.ui.draft.dialogs.DraftBottomSheet
import java.io.File
import java.io.FileNotFoundException

class DraftAdapter(private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<DraftAdapter.DraftViewHolder>() {

    private val draftsList = mutableListOf<DraftModel>()

    private var draftBottomSheet: DraftBottomSheet? = null

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

    fun appendAll(drafts: List<DraftModel>) {
        clear()
        draftsList.addAll(drafts)
        notifyDataSetChanged()
    }

    inner class DraftViewHolder(private val binding: ItemDraftBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(draft: DraftModel) {
            binding.tvDraftTitle.text = draft.title
            binding.tvLatitude.text = draft.claim.geoLocation.latitude.toString()
            binding.tvLongitude.text = draft.claim.geoLocation.longitude.toString()
            binding.tvDatetime.text = draft.claim.datetime

            if (draft.claim.url == null || !File(draft.claim.url).exists()){
                binding.ivPicture.setImageDrawable(ResourcesCompat
                    .getDrawable(binding.root.resources, R.drawable.ic_photo_placeholder, null))
            }
            else {
                try {
                    val file = File(draft.claim.url)
                    val fileUri = file.toUri()
                    binding.ivPicture.setImageURI(fileUri)
                }
                catch (e: FileNotFoundException) {
                    Log.e("FileUri", "$e")
                }
            }

            binding.root.setOnClickListener {
                if (draftBottomSheet?.isVisible != true){
                    draftBottomSheet = DraftBottomSheet().apply {
                        arguments = bundleOf(
                            DraftBottomSheet.TAG to draft
                        )
                    }
                    draftBottomSheet?.show(fragmentManager, null)
                }
            }
        }
    }
}