package com.android.hangyul.ui.screen.routine

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.hangyul.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import androidx.compose.ui.text.input.ImeAction



suspend fun searchLocation(context: Context, query: String): LatLng? = withContext(Dispatchers.IO) {
    try {
        val geocoder = Geocoder(context, Locale.KOREA)
        val result = geocoder.getFromLocationName(query, 1)
        if (!result.isNullOrEmpty()) {
            val location = result[0]
            LatLng(location.latitude, location.longitude)
        } else null
    } catch (e: Exception) {
        null
    }
}



@Composable
fun MapMarkerAddPage(navController: NavController, viewModel: MapViewModel) {
    val context = LocalContext.current
    val defaultPosition = LatLng(35.1796, 129.0756) // 부산

    val scope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 12f)
    }
    var searchQuery by remember { mutableStateOf("") }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {

        // 검색창
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("장소를 입력하세요") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    scope.launch {
                        val result = searchLocation(context, searchQuery)
                        result?.let {
                            markerPosition = it
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(it, 15f),
                                durationMs = 1000
                            )
                        }
                    }
                }
            )

        )

        // 지도
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            markerPosition?.let {
                Marker(state = MarkerState(position = it))
            }
        }

        // 검색 버튼
        Button(
            onClick = {
                markerPosition?.let {
                    viewModel.addLocation(it)
                    navController.navigate("mapList") {
                        popUpTo("mapAdd") { inclusive = true }
                    }
                }
            },
            enabled = markerPosition != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("추가")
        }
    }
}
