package com.dantariun.domain.model

data class DetectedFace(
    val boundingRect: BoundingRect,
    val contours: List<FaceContour>,
    val eyeState: EyeState,
    val mouthState: MouthState,
    val headDirection: HeadDirection
)
