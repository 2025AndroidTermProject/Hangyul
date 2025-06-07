package com.android.hangyul.ui.screen.routine

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ui.components.AddBtn
import com.android.hangyul.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapListPage(navController: NavController, viewModel: MapViewModel = viewModel()) {

    val markerPositions by viewModel.selectedLocations.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            markerPositions.lastOrNull() ?: LatLng(35.1796, 129.0756), // 기본 부산
            13f
        )
    }

    LaunchedEffect(markerPositions) {
        markerPositions.lastOrNull()?.let {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ){
        MapViewScreen(markerPositions, cameraPositionState)

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 25.dp, bottom = 25.dp),
            horizontalArrangement = Arrangement.End
        ) {
            AddBtn("장소 추가", modifier = Modifier.clickable { navController.navigate("mapAdd") })
        }

    }
}

@Preview (showBackground = true)
@Composable
fun MapListPagePreview() {
    MapListPage(navController = rememberNavController())
}