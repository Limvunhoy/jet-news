package com.example.jetnews.domain.repository

import com.example.jetnews.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val users: Flow<List<UserEntity>>
    suspend fun insertUser(user: UserEntity)
    suspend fun deleteUser(user: UserEntity)
}