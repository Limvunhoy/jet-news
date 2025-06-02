package com.example.jetnews.data.repository

import com.example.jetnews.data.model.UserDao
import com.example.jetnews.data.model.UserEntity
import com.example.jetnews.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
    override val users: Flow<List<UserEntity>>
        get() = userDao.getAllUser()

    override suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    override suspend fun deleteUser(user: UserEntity) = userDao.deleteUser(user)
}
