package com.dantariun.domain.model

data class FaceContour(
    val type: FaceContourType,
    val points: List<Point2D>
)
