package com.dantariun.domain.usecase

import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.repository.FaceDetectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFaceDetectionUseCase @Inject constructor(
    private val repository: FaceDetectionRepository
) {
    operator fun invoke(): Flow<List<DetectedFace>> = repository.detectedFaces
}
