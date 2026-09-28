package com.dantariun.domain.usecase

import com.dantariun.domain.model.BoundingRect
import com.dantariun.domain.model.DetectedFace
import com.dantariun.domain.model.EyeState
import com.dantariun.domain.model.HeadDirection
import com.dantariun.domain.model.ImageSize
import com.dantariun.domain.model.MouthState
import com.dantariun.domain.repository.FaceDetectionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveFaceDetectionUseCaseTest {

    private lateinit var repository: FaceDetectionRepository
    private lateinit var useCase: ObserveFaceDetectionUseCase

    @Before
    fun setUp() {
        repository = mockk()
        every { repository.imageSize } returns MutableStateFlow(ImageSize(640, 480))
        useCase = ObserveFaceDetectionUseCase(repository)
    }

    @Test
    fun `invoke 호출 시 repository의 detectedFaces Flow를 그대로 반환`() = runTest {
        val expected = emptyList<DetectedFace>()
        every { repository.detectedFaces } returns flowOf(expected)

        val result = useCase().first()

        assertEquals(expected, result)
    }

    @Test
    fun `감지된 얼굴 목록을 정확히 emit한다`() = runTest {
        val face = fakeDetectedFace()
        val expected = listOf(face)
        every { repository.detectedFaces } returns flowOf(expected)

        val result = useCase().first()

        assertEquals(1, result.size)
        assertEquals(face, result.first())
    }

    @Test
    fun `얼굴이 없을 때 빈 리스트를 emit한다`() = runTest {
        every { repository.detectedFaces } returns flowOf(emptyList())

        val result = useCase().first()

        assertEquals(emptyList<DetectedFace>(), result)
    }

    @Test
    fun `invoke 호출 시 repository의 detectedFaces 프로퍼티에 접근한다`() = runTest {
        every { repository.detectedFaces } returns flowOf(emptyList())

        useCase().first()

        verify(exactly = 1) { repository.detectedFaces }
    }

    @Test
    fun `여러 얼굴이 있는 경우 모두 전달된다`() = runTest {
        val faces = listOf(fakeDetectedFace(), fakeDetectedFace())
        every { repository.detectedFaces } returns flowOf(faces)

        val result = useCase().first()

        assertEquals(2, result.size)
    }

    // ── helpers ───────────────────────────────────────────────

    private fun fakeDetectedFace() = DetectedFace(
        boundingRect = BoundingRect(0, 0, 100, 100),
        contours = emptyList(),
        eyeState = EyeState(leftOpenProbability = 0.9f, rightOpenProbability = 0.9f),
        mouthState = MouthState(isOpen = false),
        headDirection = HeadDirection(eulerX = 0f, eulerY = 0f, eulerZ = 0f)
    )
}
