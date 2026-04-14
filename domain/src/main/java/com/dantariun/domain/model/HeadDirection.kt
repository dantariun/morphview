package com.dantariun.domain.model

private const val FRONT_FACING_THRESHOLD = 15f

data class HeadDirection(
    val eulerX: Float,  // pitch: 위아래 (-: 아래, +: 위)
    val eulerY: Float,  // yaw: 좌우 (-: 왼쪽, +: 오른쪽)
    val eulerZ: Float   // roll: 기울기
) {
    val isFrontFacing: Boolean get() = eulerY in -FRONT_FACING_THRESHOLD..FRONT_FACING_THRESHOLD
    val isLeftFacing: Boolean get() = eulerY < -FRONT_FACING_THRESHOLD
    val isRightFacing: Boolean get() = eulerY > FRONT_FACING_THRESHOLD
}
