package com.dantariun.domain.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EyeStateTest {

    // ── isLeftOpen ─────────────────────────────────────────────

    @Test
    fun `왼쪽 눈 - threshold 미만이면 감긴 것으로 판단`() {
        val state = EyeState(leftOpenProbability = 0.49f, rightOpenProbability = 1.0f)
        assertFalse(state.isLeftOpen)
    }

    @Test
    fun `왼쪽 눈 - threshold(0_5f) 정확히 같으면 열린 것으로 판단`() {
        val state = EyeState(leftOpenProbability = 0.5f, rightOpenProbability = 0.0f)
        assertTrue(state.isLeftOpen)
    }

    @Test
    fun `왼쪽 눈 - threshold 초과이면 열린 것으로 판단`() {
        val state = EyeState(leftOpenProbability = 0.51f, rightOpenProbability = 0.0f)
        assertTrue(state.isLeftOpen)
    }

    // ── isRightOpen ────────────────────────────────────────────

    @Test
    fun `오른쪽 눈 - threshold 미만이면 감긴 것으로 판단`() {
        val state = EyeState(leftOpenProbability = 1.0f, rightOpenProbability = 0.49f)
        assertFalse(state.isRightOpen)
    }

    @Test
    fun `오른쪽 눈 - threshold(0_5f) 정확히 같으면 열린 것으로 판단`() {
        val state = EyeState(leftOpenProbability = 0.0f, rightOpenProbability = 0.5f)
        assertTrue(state.isRightOpen)
    }

    @Test
    fun `오른쪽 눈 - threshold 초과이면 열린 것으로 판단`() {
        val state = EyeState(leftOpenProbability = 0.0f, rightOpenProbability = 0.8f)
        assertTrue(state.isRightOpen)
    }

    // ── isBothOpen ─────────────────────────────────────────────

    @Test
    fun `양쪽 모두 열렸으면 isBothOpen은 true`() {
        val state = EyeState(leftOpenProbability = 0.9f, rightOpenProbability = 0.9f)
        assertTrue(state.isBothOpen)
    }

    @Test
    fun `왼쪽만 감겼으면 isBothOpen은 false`() {
        val state = EyeState(leftOpenProbability = 0.3f, rightOpenProbability = 0.9f)
        assertFalse(state.isBothOpen)
    }

    @Test
    fun `오른쪽만 감겼으면 isBothOpen은 false`() {
        val state = EyeState(leftOpenProbability = 0.9f, rightOpenProbability = 0.3f)
        assertFalse(state.isBothOpen)
    }

    @Test
    fun `양쪽 모두 감겼으면 isBothOpen은 false`() {
        val state = EyeState(leftOpenProbability = 0.0f, rightOpenProbability = 0.0f)
        assertFalse(state.isBothOpen)
    }
}
