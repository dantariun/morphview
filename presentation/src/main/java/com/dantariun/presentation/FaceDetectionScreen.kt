package com.dantariun.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FaceDetectionScreen(
    viewModel: FaceDetectionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val detectedFaces by viewModel.detectedFaces.collectAsState()
    val imageSize by viewModel.imageSize.collectAsState()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (!hasCameraPermission) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("카메라 권한이 필요합니다.", color = Color.White, fontSize = 16.sp)
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 카메라 프리뷰
        CameraPreview(
            analyzer = viewModel.imageAnalyzer,
            modifier = Modifier.fillMaxSize()
        )

        // 얼굴 윤곽 오버레이
        FaceOverlayCanvas(
            faces = detectedFaces,
            imageSize = imageSize,
            modifier = Modifier.fillMaxSize()
        )

        // 얼굴 상태 패널
        FaceStatePanel(
            face = detectedFaces.firstOrNull(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
