package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.hangyul.R
import com.android.hangyul.ui.components.ListBtn

data class routineList(
    val icon: Int,
    val text: String,
    val description: String,
)

val medicine = routineList(
    icon = R.drawable.ic_routine_pill,
    text = "약 복용 알림 설정",
    description = "약 복용 시간을 설정하고 알림을 받습니다."
)

val location = routineList(
    icon = R.drawable.ic_routine_map,
    text = "자주 가는 위치 등록",
    description = "자주 가는 위치를 등록하고 지도에서 확인\n할 수 있습니다."
)

@Composable
fun RoutinePage(navController: NavController) {
    Column  (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),

    ){
        ListBtn(medicine.icon, medicine.text, medicine.description, modifier = Modifier.clickable {
            navController.navigate("alarmList")
        })
        Spacer(modifier = Modifier.height(15.dp))
        ListBtn(location.icon, location.text, location.description, modifier = Modifier.clickable {
            navController.navigate("mapList")
        })
    }
}

@Preview
@Composable
fun RouTinePagePreview() {
    RoutinePage(navController = androidx.navigation.compose.rememberNavController())
}
