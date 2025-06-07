package com.android.hangyul.ui.screen.routine

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale


fun searchLocation(
    context: Context,
    query: String,
    onResult: (LatLng) -> Unit
) {
    val geocoder = Geocoder(context, Locale.KOREA)
    val addresses = geocoder.getFromLocationName(query, 1)
    if (!addresses.isNullOrEmpty()) {
        val location = addresses[0]
        onResult(LatLng(location.latitude, location.longitude))
    }
}


@Composable
fun MapMarkerAddPage() {
    val context = LocalContext.current
    val defaultPosition = LatLng(35.1796, 129.0756) // 부산

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
                .padding(16.dp)
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
                searchLocation(context, searchQuery) { latLng ->
                    markerPosition = latLng
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("추가")
        }
    }
}

@Preview
@Composable
fun MapMarkerAddPagePreview() {
    MapMarkerAddPage()
}