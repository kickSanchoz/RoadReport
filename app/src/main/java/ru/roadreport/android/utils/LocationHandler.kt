package ru.roadreport.android.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.roadreport.android.R
import ru.roadreport.android.data.domain.models.GeoLocation

interface ILocation {
    fun locationListener(block: (GeoLocation?) -> Unit)
}

class LocationHandler(
    private var fragment: Fragment?
): DefaultLifecycleObserver, ILocation {

    private lateinit var callback: (GeoLocation?) -> Unit

    private var alertDialog: AlertDialog? = null
    private val locationPermissionRequest = fragment?.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        when {
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.e("Permissions", "precise location access granted")
                getLocation()
            }
            permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.e("Permissions", "approximate location access granted")
                getLocation()
            }
            else -> {
                Log.e("Permissions", "no granted")
                callback(null)

                fragment?.let { frgmnt ->
                    if (alertDialog == null){
                        alertDialog = frgmnt.context?.let { cntxt ->
                            MaterialAlertDialogBuilder(cntxt).apply {
                                setMessage(cntxt.getString(R.string.LocationRequiredDescription))
                                setPositiveButton(cntxt.getString(R.string.Provide)) { _, _ ->
                                    frgmnt.startActivity(
                                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                                }
                                setNegativeButton(cntxt.getString(R.string.Cancel), null)
                            }.show()
                        }
                    }
                    else {
                        if (alertDialog?.isShowing == false) {
                            alertDialog?.show()
                        }
                    }
                }
            }
        }
    }

    private fun getLocation() {
        fragment?.let { frgmnt ->
            frgmnt.context?.let { cntxt ->

                //Для функции getFusedLocationProviderClient обязательно требуется проверка
                if (ActivityCompat.checkSelfPermission(cntxt,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(cntxt,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ) {
                    val fusedLocationProviderClient = LocationServices
                        .getFusedLocationProviderClient(frgmnt.requireActivity())
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        Log.e("Location", "${it.latitude}, ${it.longitude}")
                        callback(GeoLocation(
                            latitude = it.latitude,
                            longitude = it.longitude
                        ))
                    }
                }
            }
        }

    }

    override fun locationListener(block: (GeoLocation?) -> Unit) {
        callback = block

        fragment?.let { frgmnt ->
            frgmnt.context?.let { cntxt ->
                if (ActivityCompat.checkSelfPermission(cntxt,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(cntxt,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionRequest?.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION))
                }
                else {
                    getLocation()
                }
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        fragment = null
    }
}