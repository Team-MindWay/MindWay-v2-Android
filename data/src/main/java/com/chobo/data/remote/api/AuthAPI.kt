package com.chobo.data.remote.api

import com.chobo.data.remote.dto.auth.request.GAuthPostRequestBody
import com.chobo.data.remote.dto.auth.response.GAuthLoginResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthAPI {
    @POST("/api/v2/auth")
    suspend fun gAuthLogin(
        @Body body: GAuthPostRequestBody
    ): GAuthLoginResponse

    @PATCH("/api/v2/auth")
    suspend fun gAuthAccess(): GAuthLoginResponse

    @DELETE("/api/v2/auth")
    fun gAuthLogout()
}