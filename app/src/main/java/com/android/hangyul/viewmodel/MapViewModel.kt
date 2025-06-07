package com.android.hangyul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng


class MapViewModel : ViewModel(){
    var selectedLocation by mutableStateOf<LatLng?> (null)
        private set

    fun setLocation(latLng: LatLng) {
        selectedLocation = latLng
    }
}