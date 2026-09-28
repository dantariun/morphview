package com.dantariun.domain.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HeadDirectionTest {

    // ── isFrontFacing ──────────────────────────────────────────

    @Test
    fun `eulerY가 0이면 정면`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 0f, eulerZ = 0f)
        assertTrue(direction.isFrontFacing)
    }

    @Test
    fun `eulerY가 -15f 이면 정면 (경계 포함)`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = -15f, eulerZ = 0f)
        assertTrue(direction.isFrontFacing)
    }

    @Test
    fun `eulerY가 15f 이면 정면 (경계 포함)`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 15f, eulerZ = 0f)
        assertTrue(direction.isFrontFacing)
    }

    @Test
    fun `eulerY가 -15f 초과 음수이면 정면이 아님`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = -15.1f, eulerZ = 0f)
        assertFalse(direction.isFrontFacing)
    }

    @Test
    fun `eulerY가 15f 초과이면 정면이 아님`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 15.1f, eulerZ = 0f)
        assertFalse(direction.isFrontFacing)
    }

    // ── isLeftFacing ───────────────────────────────────────────

    @Test
    fun `eulerY가 threshold 미만 음수이면 왼쪽을 보는 것으로 판단`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = -30f, eulerZ = 0f)
        assertTrue(direction.isLeftFacing)
    }

    @Test
    fun `eulerY가 -15f 이면 왼쪽이 아님 (경계는 정면)`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = -15f, eulerZ = 0f)
        assertFalse(direction.isLeftFacing)
    }

    @Test
    fun `eulerY가 양수이면 왼쪽을 보는 것이 아님`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 30f, eulerZ = 0f)
        assertFalse(direction.isLeftFacing)
    }

    // ── isRightFacing ──────────────────────────────────────────

    @Test
    fun `eulerY가 threshold 초과 양수이면 오른쪽을 보는 것으로 판단`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 30f, eulerZ = 0f)
        assertTrue(direction.isRightFacing)
    }

    @Test
    fun `eulerY가 15f 이면 오른쪽이 아님 (경계는 정면)`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 15f, eulerZ = 0f)
        assertFalse(direction.isRightFacing)
    }

    @Test
    fun `eulerY가 음수이면 오른쪽을 보는 것이 아님`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = -30f, eulerZ = 0f)
        assertFalse(direction.isRightFacing)
    }

    // ── 상호 배타성 ────────────────────────────────────────────

    @Test
    fun `정면일 때 왼쪽과 오른쪽은 false`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 0f, eulerZ = 0f)
        assertFalse(direction.isLeftFacing)
        assertFalse(direction.isRightFacing)
    }

    @Test
    fun `왼쪽일 때 정면과 오른쪽은 false`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = -45f, eulerZ = 0f)
        assertFalse(direction.isFrontFacing)
        assertFalse(direction.isRightFacing)
    }

    @Test
    fun `오른쪽일 때 정면과 왼쪽은 false`() {
        val direction = HeadDirection(eulerX = 0f, eulerY = 45f, eulerZ = 0f)
        assertFalse(direction.isFrontFacing)
        assertFalse(direction.isLeftFacing)
    }
}
