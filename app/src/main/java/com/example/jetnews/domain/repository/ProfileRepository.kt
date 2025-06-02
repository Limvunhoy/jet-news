package com.example.jetnews.domain.repository

import com.example.jetnews.data.model.ProfileResponse
import com.example.jetnews.data.repository.Result

interface ProfileRepository {
    suspend fun getProfile(jsonBody: String) : Result<ProfileResponse>
}