package com.dantariun.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantariun.data.repository.FaceDetectionRepositoryImpl
import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.model.ImageSize
import com.dantariun.domain.usecase.ObserveFaceDetectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FaceDetectionViewModel @Inject constructor(
    private val repository: FaceDetectionRepositoryImpl,
    observeFaceDetection: ObserveFaceDetectionUseCase
) : ViewModel() {

    val detectedFaces: StateFlow<List<DetectedFace>> = observeFaceDetection()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val imageSize: StateFlow<ImageSize> = repository.imageSize

    val imageAnalyzer get() = repository.imageAnalyzer

    override fun onCleared() {
        super.onCleared()
        repository.stopDetection()
    }
}
