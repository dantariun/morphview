package com.dantariun.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.model.FaceContourType
import com.dantariun.domain.model.ImageSize

private val BOUNDING_BOX_COLOR = Color(0xFF00BFFF)
private const val STROKE_WIDTH = 3f

private fun contourColor(type: FaceContourType): Color = when (type) {
    FaceContourType.FACE -> Color(0xFF00FF00)
    FaceContourType.LEFT_EYE, FaceContourType.RIGHT_EYE -> Color(0xFFFFFF00)
    FaceContourType.UPPER_LIP_TOP, FaceContourType.UPPER_LIP_BOTTOM,
    FaceContourType.LOWER_LIP_TOP, FaceContourType.LOWER_LIP_BOTTOM -> Color(0xFFFF6B6B)
    else -> Color(0xFF00FF00)
}

@Composable
fun FaceOverlayCanvas(
    faces: List<DetectedFace>,
    imageSize: ImageSize,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (imageSize.width == 0 || imageSize.height == 0) return@Canvas

        val scaleX = size.width / imageSize.width.toFloat()
        val scaleY = size.height / imageSize.height.toFloat()

        faces.forEach { face ->
            // 바운딩 박스 (전면 카메라 X 반전)
            val rect = face.boundingRect
            drawRect(
                color = BOUNDING_BOX_COLOR,
                topLeft = Offset(
                    x = size.width - rect.right * scaleX,
                    y = rect.top * scaleY
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = rect.width * scaleX,
                    height = rect.height * scaleY
                ),
                style = Stroke(width = STROKE_WIDTH)
            )

            // 윤곽선
            face.contours.forEach { contour ->
                if (contour.points.size < 2) return@forEach
                val path = Path()
                contour.points.forEachIndexed { index, point ->
                    val x = size.width - point.x * scaleX  // 전면 카메라 X 반전
                    val y = point.y * scaleY
                    if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                if (contour.type == FaceContourType.FACE) path.close()

                drawPath(
                    path = path,
                    color = contourColor(contour.type),
                    style = Stroke(width = STROKE_WIDTH)
                )
            }
        }
    }
}
