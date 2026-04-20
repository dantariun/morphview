package com.dantariun.data.mapper

import com.dantariun.domain.model.BoundingRect
import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.model.EyeState
import com.dantariun.domain.model.FaceContour
import com.dantariun.domain.model.FaceContourType
import com.dantariun.domain.model.HeadDirection
import com.dantariun.domain.model.MouthState
import com.dantariun.domain.model.Point2D
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour as MlKitFaceContour

private const val MOUTH_OPEN_THRESHOLD = 10f

internal fun Face.toDomain(): DetectedFace = DetectedFace(
    boundingRect = boundingBox.run {
        BoundingRect(left = left, top = top, right = right, bottom = bottom)
    },
    contours = allContours.mapNotNull { it.toDomain() },
    eyeState = EyeState(
        leftOpenProbability = leftEyeOpenProbability ?: 0f,
        rightOpenProbability = rightEyeOpenProbability ?: 0f
    ),
    mouthState = detectMouthState(),
    headDirection = HeadDirection(
        eulerX = headEulerAngleX,
        eulerY = headEulerAngleY,
        eulerZ = headEulerAngleZ
    )
)

private fun MlKitFaceContour.toDomain(): FaceContour? {
    val domainType = when (faceContourType) {
        MlKitFaceContour.FACE -> FaceContourType.FACE
        MlKitFaceContour.LEFT_EYEBROW_TOP -> FaceContourType.LEFT_EYEBROW_TOP
        MlKitFaceContour.LEFT_EYEBROW_BOTTOM -> FaceContourType.LEFT_EYEBROW_BOTTOM
        MlKitFaceContour.RIGHT_EYEBROW_TOP -> FaceContourType.RIGHT_EYEBROW_TOP
        MlKitFaceContour.RIGHT_EYEBROW_BOTTOM -> FaceContourType.RIGHT_EYEBROW_BOTTOM
        MlKitFaceContour.LEFT_EYE -> FaceContourType.LEFT_EYE
        MlKitFaceContour.RIGHT_EYE -> FaceContourType.RIGHT_EYE
        MlKitFaceContour.UPPER_LIP_TOP -> FaceContourType.UPPER_LIP_TOP
        MlKitFaceContour.UPPER_LIP_BOTTOM -> FaceContourType.UPPER_LIP_BOTTOM
        MlKitFaceContour.LOWER_LIP_TOP -> FaceContourType.LOWER_LIP_TOP
        MlKitFaceContour.LOWER_LIP_BOTTOM -> FaceContourType.LOWER_LIP_BOTTOM
        MlKitFaceContour.NOSE_BRIDGE -> FaceContourType.NOSE_BRIDGE
        MlKitFaceContour.NOSE_BOTTOM -> FaceContourType.NOSE_BOTTOM
        else -> return null
    }
    return FaceContour(
        type = domainType,
        points = points.map { Point2D(x = it.x, y = it.y) }
    )
}

private fun Face.detectMouthState(): MouthState {
    val upperLipBottom = getContour(MlKitFaceContour.UPPER_LIP_BOTTOM)?.points
    val lowerLipTop = getContour(MlKitFaceContour.LOWER_LIP_TOP)?.points

    if (upperLipBottom.isNullOrEmpty() || lowerLipTop.isNullOrEmpty()) {
        return MouthState(isOpen = false)
    }

    val upperAvgY = upperLipBottom.map { it.y }.average().toFloat()
    val lowerAvgY = lowerLipTop.map { it.y }.average().toFloat()
    val gap = lowerAvgY - upperAvgY

    return MouthState(isOpen = gap > MOUTH_OPEN_THRESHOLD)
}
