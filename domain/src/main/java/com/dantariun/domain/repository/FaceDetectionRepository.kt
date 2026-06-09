package com.dantariun.domain.repository

import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.model.ImageSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FaceDetectionRepository {
    val detectedFaces: Flow<List<DetectedFace>>
    val imageSize: StateFlow<ImageSize>
    fun startDetection()
    fun stopDetection()
}
