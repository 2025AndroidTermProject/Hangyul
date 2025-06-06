package com.android.hangyul.ui.components

import android.health.connect.datatypes.ExerciseRoute
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.hangyul.R
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ui.theme.HangyulTheme


@Composable
fun NaviBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color.White)
            .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
            .navigationBarsPadding()


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(R.drawable.ic_nav_diary, "음성 일기", navController, "diary")
            NavItem(R.drawable.ic_nav_routine, "일상 관리", navController, "routine")
            NavItem(R.drawable.ic_nav_main, "홈", navController, "main")
            NavItem(R.drawable.ic_nav_braintraining, "두뇌 훈련", navController, "brainTraining")
            NavItem(R.drawable.ic_nav_memory, "추억 기록", navController, "memory")

        }
    }
}
@Composable
@Preview
fun NaviBarPreview(){
    HangyulTheme {
        NaviBar(navController = rememberNavController())
    }
}
@Composable
fun NavItem(icon:Int, page: String, navController: NavController, route: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    ){
        Image(
            painter = painterResource(id = icon),
            contentDescription = page,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = page,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}


