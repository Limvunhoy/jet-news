package com.example.jetnews.data.repository

import com.example.jetnews.data.model.ProfileResponse
import com.example.jetnews.data.model.ProfileResponseParser
import com.example.jetnews.domain.repository.ProfileRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class ProfileRepositoryImpl @Inject constructor(
    private val responseParser: ProfileResponseParser
) : ProfileRepository {

    private val getProfileUrl = "https://example.com/profile"

    override suspend fun getProfile(jsonBody: String): Result<ProfileResponse> {
        return withContext(Dispatchers.IO) {
            try {
//                val url = URL(getProfileUrl)
//                val connection = (url.openConnection() as HttpURLConnection).apply {
//                    requestMethod = "POST"
//                    setRequestProperty("Content-Type", "application/json; utf-8")
//                    setRequestProperty("Accept", "application/json")
//                    doOutput = true
//                    outputStream.use { it.write(jsonBody.toByteArray()) }
//                }

//                connection.inputStream.use { input ->
//                    val parsedResponse = responseParser.parse(input)
//                    Result.Success(parsedResponse)
//                }
                val inputStream: InputStream = jsonBody.byteInputStream()
                val parsedResponse = responseParser.parse(inputStream)
                Result.Success(parsedResponse)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
