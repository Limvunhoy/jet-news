package com.example.jetnews.di

import com.example.jetnews.data.model.ProfileResponseParser
import com.example.jetnews.data.model.UserDao
import com.example.jetnews.data.repository.ProfileRepositoryImpl
import com.example.jetnews.data.repository.UserRepositoryImpl
import com.example.jetnews.domain.repository.ProfileRepository
import com.example.jetnews.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    @Provides
    fun provideProfileResponseParser(): ProfileResponseParser {
        return ProfileResponseParser() // or build it with config
    }
}