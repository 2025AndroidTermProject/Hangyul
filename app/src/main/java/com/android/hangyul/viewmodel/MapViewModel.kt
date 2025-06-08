package com.android.hangyul.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.hangyul.ui.screen.routine.location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

data class MapMarker(
    val id: String = UUID.randomUUID().toString(),
    val lat: Double,
    val lng: Double
)

fun MapMarker.toMap(): Map<String, Any> = mapOf(
    "id" to id,
    "lat" to lat,
    "lng" to lng
)


class MapViewModel : ViewModel() {

    val db = Firebase.firestore

    private val _selectedLocations = MutableStateFlow<List<LatLng>>(emptyList())
    val selectedLocations = _selectedLocations.asStateFlow()

    fun addLocation(location: LatLng) {
        _selectedLocations.value = _selectedLocations.value + location

        val marker = MapMarker(lat = location.latitude, lng = location.longitude)
        db.collection("mapMarkers").document(marker.id)
            .set(marker.toMap())
            .addOnSuccessListener { Log.d("MapDebug", "마커 저장 성공") }
            .addOnFailureListener { Log.e("MapDebug", "마커 저장 실패", it) }
    }

    fun fetchLocations() {
        db.collection("mapMarkers")
            .get()
            .addOnSuccessListener { result ->
                val markerList = result.mapNotNull { doc ->
                    val lat = doc.getDouble("lat")
                    val lng = doc.getDouble("lng")
                    if (lat != null && lng != null) LatLng(lat, lng) else null
                }
                _selectedLocations.value = markerList
            }
    }
}