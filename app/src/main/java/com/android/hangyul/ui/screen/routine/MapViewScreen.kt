package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapViewScreen() {
    val context = LocalContext.current
    val busanLatLng = LatLng(35.1796, 129.0756)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(busanLatLng, 13f)
    }

    if(context != null) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),  // 비율 조정
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = busanLatLng),
                title = "부산",
                snippet = "대한민국"
            )
        }
    }

}

@Preview
@Composable
fun MapViewScreenPreview() {
    MapViewScreen()
}