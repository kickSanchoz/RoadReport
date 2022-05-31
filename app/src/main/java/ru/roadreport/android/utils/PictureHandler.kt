package ru.roadreport.android.utils

import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.roadreport.android.BuildConfig
import ru.roadreport.android.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

interface ISelectPicture {
    fun showPicker(block: (File?) -> Unit)
    fun deletePicture(file: File)
}

class PictureHandler (
    private var fragment: Fragment?
): DefaultLifecycleObserver, ISelectPicture {

    init {
        fragment?.lifecycle?.addObserver(this)
    }

    private lateinit var callback: (File?) -> Unit

    private fun createImageFile(): File {
        val storageDir = fragment?.requireActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        Log.e("storageDir", "$storageDir")
        val prefix = SimpleDateFormat(FILE_DATE_FORMAT).format(Date())
        return File.createTempFile("rr_$prefix", ".jpg", storageDir)
    }

    private fun deleteFile(file: File) {
        file.let { f ->
            if (f.exists()) {
                f.delete()
                Log.e("File status", "deleted")
            }
            else {
                Log.e("File exception", "file not found")
            }
        }
    }

    private fun copyFile(uri: Uri, file: File): Boolean {
        fragment?.context?.let {
            val inputStream: InputStream? = it.contentResolver.openInputStream(uri)
            if (inputStream == null){
                return false
            }
            else {
                val outputStream: FileOutputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()

                return true
            }

        }
        return false
    }


    //--------------------------------From Device--------------------------------
    private var selectUri: Uri? = null
    private val selectPictureLauncher =
        fragment?.registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Log.e("selectUri", "$uri")

            if (uri == null) {
                callback(null)
            }
            else {
                fragment?.context?.let {
                    createImageFile().also { file ->
                        val success = copyFile(uri, file)
                        if (!success){
                            deleteFile(file)
                            callback(null)
                        }
                        else {
                            callback(file)
                        }
                    }
                }
            }
    }


    private fun getFromDevice() {
        selectPictureLauncher?.launch("image/*")
    }


    //--------------------------------From Camera--------------------------------
    private var imageUri: Uri? = null
    private var imagePath: String = ""
    private val cameraLauncher =
        fragment?.registerForActivityResult(ActivityResultContracts.TakePicture()) {

        if (it) {
            val file = File(imagePath)
            callback(file)
        }
        else {
            File(imagePath).also { f ->
                if (f.exists()) {
                    f.delete()
                }
                else {
                    Log.e("File exception", "file not found")
                }
            }
            callback(null)
        }
    }

    private fun getFromCamera() {
        fragment?.context?.let {context ->
            imageUri = FileProvider.getUriForFile(context,
                "${BuildConfig.APPLICATION_ID}.provider", createImageFile().also {
                    imagePath = it.absolutePath
                    Log.e("imagePath", imagePath)
                })

            if (imageUri == null) {
                Log.e("imageUri", "Failed to get imageUri of new file")
                callback(null)
            }
            else {
                cameraLauncher?.launch(imageUri)
            }
        }
    }

    //--------------------------------Choose--------------------------------
    private val items =
        arrayOf(
            fragment?.context?.getString(R.string.TakePhoto),
            fragment?.context?.getString(R.string.SelectFromGallery)
        )
    private var alertDialog: androidx.appcompat.app.AlertDialog? = null

    override fun showPicker(block: (File?) -> Unit) {
        callback = block

        if (alertDialog == null) {
            alertDialog = fragment?.context?.let {
                MaterialAlertDialogBuilder(it)
                    .setItems(items) { dialog, which ->
                        when (which) {
                            0 -> getFromCamera()
                            1 -> getFromDevice()
                            else -> {
                            }
                        }
                        dialog.dismiss()
                    }.show()
            }
        } else {
            if (alertDialog?.isShowing == false) {
                alertDialog?.show()
            }
        }
    }

    override fun deletePicture(file: File) {
        deleteFile(file)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        fragment = null
    }
}