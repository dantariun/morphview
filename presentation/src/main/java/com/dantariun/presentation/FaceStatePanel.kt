package com.dantariun.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dantariun.domain.model.DetectedFace

@Composable
fun FaceStatePanel(
    face: DetectedFace?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (face == null) {
            Text("얼굴을 감지하는 중...", color = Color.White, fontSize = 14.sp)
            return@Column
        }

        Text("얼굴 감지됨", color = Color.Green, fontSize = 16.sp, fontWeight = FontWeight.Bold)

        StateRow(label = "왼쪽 눈", value = if (face.eyeState.isLeftOpen) "뜸" else "감음")
        StateRow(label = "오른쪽 눈", value = if (face.eyeState.isRightOpen) "뜸" else "감음")
        StateRow(label = "입", value = if (face.mouthState.isOpen) "벌림" else "다물음")
        StateRow(
            label = "방향",
            value = when {
                face.headDirection.isFrontFacing -> "정면"
                face.headDirection.isLeftFacing -> "왼쪽"
                else -> "오른쪽"
            }
        )
    }
}

@Composable
private fun StateRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.LightGray, fontSize = 13.sp)
        Text(text = value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}
