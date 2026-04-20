package com.dantariun.data.repository

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.dantariun.data.mapper.toDomain
import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.repository.FaceDetectionRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FaceDetectionRepositoryImpl : FaceDetectionRepository {

    private val _detectedFaces = MutableStateFlow<List<DetectedFace>>(emptyList())
    override val detectedFaces: Flow<List<DetectedFace>> = _detectedFaces.asStateFlow()

    private val detector: FaceDetector by lazy {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .enableTracking()
            .build()
        FaceDetection.getClient(options)
    }

    // CameraX ImageAnalysis.Analyzer — presentation 레이어에서 CameraX에 등록
    val imageAnalyzer = ImageAnalysis.Analyzer { imageProxy ->
        processImage(imageProxy)
    }

    private fun processImage(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }
        val inputImage = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )
        detector.process(inputImage)
            .addOnSuccessListener { faces ->
                _detectedFaces.value = faces.map { it.toDomain() }
            }
            .addOnFailureListener {
                _detectedFaces.value = emptyList()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    override fun startDetection() {
        _detectedFaces.value = emptyList()
    }

    override fun stopDetection() {
        _detectedFaces.value = emptyList()
        detector.close()
    }
}
