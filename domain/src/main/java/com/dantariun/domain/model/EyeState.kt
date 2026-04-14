package com.dantariun.domain.model

private const val EYE_OPEN_THRESHOLD = 0.5f

data class EyeState(
    val leftOpenProbability: Float,   // 0.0 ~ 1.0
    val rightOpenProbability: Float
) {
    val isLeftOpen: Boolean get() = leftOpenProbability >= EYE_OPEN_THRESHOLD
    val isRightOpen: Boolean get() = rightOpenProbability >= EYE_OPEN_THRESHOLD
    val isBothOpen: Boolean get() = isLeftOpen && isRightOpen
}
