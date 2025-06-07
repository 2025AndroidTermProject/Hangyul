package com.android.hangyul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.hangyul.ui.screen.routine.location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MapViewModel : ViewModel() {
    private val _selectedLocations = MutableStateFlow<List<LatLng>>(emptyList())
    val selectedLocations = _selectedLocations.asStateFlow()

    fun addLocation(location: LatLng) {
        _selectedLocations.value = _selectedLocations.value + location
    }
}