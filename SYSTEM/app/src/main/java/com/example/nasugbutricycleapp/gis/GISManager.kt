package com.example.nasugbutricycleapp.gis

import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style

object GISManager {

    fun loadGISLayers(map: MapLibreMap) {
        val style = map.style ?: return

        loadServiceZones(style)
        loadTerminals(style)
        loadNoParkingZones(style)
    }

    private fun loadServiceZones(style: Style) {
        // Service zone GIS data will be loaded here.
    }

    private fun loadTerminals(style: Style) {
        // Sakayan and babaan GIS data will be loaded here.
    }

    private fun loadNoParkingZones(style: Style) {
        // No-parking GIS data will be loaded here.
    }
}