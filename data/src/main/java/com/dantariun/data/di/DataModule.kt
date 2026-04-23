package com.dantariun.data.di

import com.dantariun.data.repository.FaceDetectionRepositoryImpl
import com.dantariun.domain.repository.FaceDetectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindFaceDetectionRepository(
        impl: FaceDetectionRepositoryImpl
    ): FaceDetectionRepository
}
