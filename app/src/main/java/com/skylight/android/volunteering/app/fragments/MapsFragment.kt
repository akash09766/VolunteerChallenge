package com.skylight.android.volunteering.app.fragments

import com.google.android.gms.maps.model.*
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.core.ui.base.BaseFragment


class MapsFragment : BaseFragment(R.layout.fragment_maps) {

    /**
     * Simple fragment which is hosting google map to show current location of each vehicles
     */

    @Suppress("unused")
    private val TAG by lazy {
        MapsFragment::class.java.simpleName
    }

//    val args: MapsFragmentArgs by navArgs()
/*
    private val callback = OnMapReadyCallback { googleMap ->

        val isNightTime = googleMap.isNightTime()

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context?.applicationContext!!,
                    if (isNightTime) R.raw.google_night_mode else R.raw.google_day_mode
                )
            )
            if (!success) {
                Timber.e("$TAG Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Timber.e("$TAG Can't find style. Error: " + e.message)
        }

        args.allVehicleData.poiList?.forEach { markerData ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            markerData?.coordinate?.latitude!!,
                            markerData?.coordinate?.longitude!!
                        )
                    )
                    .anchor(0.5f, 0.5f)
                    .title(markerData.id.toString())
                    .snippet(markerData.fleetType)
                    .icon(
                        bitmapDescriptorFromVector(
                            requireContext(),
                            if (isNightTime) R.drawable.ic_local_taxi_white else R.drawable.ic_local_taxi_black
                        )
                    )
            )

            val center = CameraUpdateFactory.newLatLng(
                LatLng(
                    args.selectedVehicle.coordinate?.latitude!!,
                    args.selectedVehicle.coordinate?.longitude!!
                )
            )
            val zoom = CameraUpdateFactory.zoomTo(13.5f)

            googleMap.moveCamera(center)
            googleMap.animateCamera(zoom, 1000, null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        Timber.d("$TAG onViewCreated: ${args.selectedVehicle}")
        Timber.d("$TAG  onViewCreated: ${args.allVehicleData}")
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }*/
}
