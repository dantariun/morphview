package com.dantariun.domain.repository

import com.dantariun.domain.model.DetectedFace
import kotlinx.coroutines.flow.Flow

interface FaceDetectionRepository {
    val detectedFaces: Flow<List<DetectedFace>>
    fun startDetection()
    fun stopDetection()
}
