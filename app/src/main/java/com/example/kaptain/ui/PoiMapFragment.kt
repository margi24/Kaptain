package com.example.kaptain.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kaptain.R
import com.example.kaptain.data.PoiData
import com.example.kaptain.data.poiList
import com.example.kaptain.viewModel.PoiViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PoiMapFragment : Fragment(R.layout.poi_map_fragment), GoogleMap.OnInfoWindowClickListener {

    private var poiData = listOf<PoiData>()
    private lateinit var mapFragment: SupportMapFragment
    private val viewModel: PoiViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        view.doOnLayout {
            refreshMap()
        }
        viewModel.getPoiDataList().observe(viewLifecycleOwner, Observer {
            it?.let {
                poiData = it
            }
        })
    }

    override fun onInfoWindowClick(selectedMarker: Marker?) {
        selectedMarker?.let { marker ->
            val poi = poiData.find {
                it.mapLocation.latitude == marker.position.latitude && it.mapLocation.longitude == marker.position.longitude
            }
            poi?.let {poiData ->
                findNavController().navigate(
                        PoiMapFragmentDirections.actionPoiMapFragmentToPoiDetailsFragment(
                            poiData.poi.id,
                            poiData.poi.name,
                            poiData.poi.poiType)
                )
            }
        }
    }

    private fun refreshMap() {
        mapFragment.getMapAsync { map ->
            map.setOnInfoWindowClickListener(this)
            val latLngBoundsBuilder = LatLngBounds.builder()
            poiData.forEach { poi ->
                LatLng(poi.mapLocation.latitude, poi.mapLocation.longitude).also {
                    latLngBoundsBuilder.include(it)
                    map.addMarker(MarkerOptions().position(it).title(poi.poi.name))
                }
            }
            map.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(), PADDING)
            )
        }
    }

    companion object {
        private const val PADDING = 100
    }
}