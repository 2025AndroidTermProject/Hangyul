package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapViewScreen(
    markerPositions: List<LatLng>,
    cameraPositionState: CameraPositionState
) {
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        cameraPositionState = cameraPositionState
    ) {
        markerPositions.forEachIndexed { index, position ->
            Marker(
                state = MarkerState(position = position),
                title = "선택된 장소 ${index + 1}",
                snippet = "위도: ${position.latitude}, 경도: ${position.longitude}"
            )
        }
    }
}

