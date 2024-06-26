package com.chobo.domain.usecase.auth

import com.chobo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRefreshToken @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<String> =
        authRepository.getGAuthAccess()
}