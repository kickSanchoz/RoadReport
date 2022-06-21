package ru.roadreport.android.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.roadreport.android.R
import ru.roadreport.android.data.domain.models.GeoLocation

interface ILocationHandler {
    fun locationListener(block: (GeoLocation?) -> Unit)
}

// При нестандартных кейсах, диалоги появляются не в том порядке

class LocationHandler(
    private var fragment: Fragment?
): DefaultLifecycleObserver, ILocationHandler, LocationListener {

    init {
        fragment?.lifecycle?.addObserver(this)
    }

    private lateinit var callback: (GeoLocation?) -> Unit

    private var alertDialog: AlertDialog? = null
    private var locationManager: LocationManager? = null

    private val locationPermissionRequest = fragment?.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        when {
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.e("Permissions", "precise location access granted")
                getCurrentLocation()
            }
            permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.e("Permissions", "approximate location access granted")
                getCurrentLocation()
            }
            else -> {
                Log.e("Permissions", "no granted")
                callback(null)

                val action: (() -> Unit) = {
                    fragment?.context?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }

                fragment?.context?.let { cntxt ->
                    showAlertDialog(
                        message = cntxt.getString(R.string.LocationPermissionRequiredDescription),
                        posButtonTitle = cntxt.getString(R.string.Provide),
                        posButtonAction = action
                    )
                }
            }
        }
    }

    override fun locationListener(block: (GeoLocation?) -> Unit) {
        callback = block

        getCurrentLocation()
    }

    //Не удалось убрать вложенность из-за неправильного порядка диалогов
    private fun getCurrentLocation() {
        fragment?.let { frgmnt ->
            frgmnt.context?.let { cntxt ->
                // Разрешения не получены
                if (!checkPermissions()) {
                    locationPermissionRequest?.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
                else {
                    // Служба геолокации не включена
                    if (!isLocationEnabled()) {
                        showAlertDialog(
                            message = cntxt.getString(R.string.LocationServiceRequiredDescription),
                            posButtonTitle = cntxt.getString(R.string.Ok),
                            negBtnVisible = false
                        )
                    }
                    else {
                        // Повторная проверка необходима для методов getLastKnownLocation
                        if (ActivityCompat.checkSelfPermission(cntxt,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(cntxt,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                        ) {
                            locationManager = cntxt.getSystemService(Context.LOCATION_SERVICE)
                                    as LocationManager

                            locationManager?.let { lm ->
                                val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (location == null) {
                                    lm.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        5000L,
                                        0f,
                                        this@LocationHandler)

                                    showAlertDialog(
                                        message = cntxt.getString(R.string.LocationServiceNotReady),
                                        posButtonTitle = cntxt.getString(R.string.Ok),
                                        negBtnVisible = false
                                    )
                                } else {
                                    Log.e("Location",
                                        "${location.latitude}, ${location.longitude}"
                                    )
                                    callback(GeoLocation(
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    ))
                                }
                            }

                        } else {

                        }
                    }
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val context = fragment?.context
        if (context == null){
            Log.e("Context", "null")
            return false
        }
        else {
            return (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val context = fragment?.context
        if (context == null){
            Log.e("Location status", "disabled")
            return false
        }
        else {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

    private fun showAlertDialog(message: String,
                                posButtonTitle: String,
                                posButtonAction: (() -> Unit)? = null,
                                negBtnVisible: Boolean = true) {
        fragment?.context?.let {
            if (alertDialog == null){
                alertDialog = MaterialAlertDialogBuilder(it).apply {
                    setMessage(message)
                    setPositiveButton(posButtonTitle) { _, _ ->
                        posButtonAction?.invoke()
                    }
                    if (negBtnVisible){
                        setNegativeButton(context.getString(R.string.Back), null)
                    }
                }.show()
            }
            else {
                if (alertDialog?.isShowing == false) {
                    alertDialog?.show()
                }
            }
        }

    }

    override fun onLocationChanged(location: Location) {
        println(location)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        locationManager?.removeUpdates(this)
        fragment = null
    }


}