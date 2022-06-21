package ru.roadreport.android.utils.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import ru.roadreport.android.databinding.ItemGalleryPhotoBinding
import java.io.File

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private val photoFileList = mutableListOf<File>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = ItemGalleryPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(photoFileList[position])
    }

    override fun getItemCount(): Int {
        return photoFileList.size
    }

    private fun clear() {
        photoFileList.clear()
        notifyDataSetChanged()
    }

    fun appendAll(photoFiles: List<File>) {
        clear()
        photoFileList.addAll(photoFiles)
        notifyDataSetChanged()
    }

    inner class GalleryViewHolder(private val binding: ItemGalleryPhotoBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoFile: File) {
            binding.pvPhoto.load(photoFile){
                scale(Scale.FIT)
//                error("error")
            }
        }
    }
}