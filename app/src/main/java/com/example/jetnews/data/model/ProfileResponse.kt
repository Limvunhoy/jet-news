package com.example.jetnews.data.model

import java.io.InputStream
import java.nio.charset.Charset
import org.json.JSONObject

data class ProfileResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
)

class ProfileResponseParser {
    fun parse(inputStream: InputStream): ProfileResponse {
        val responseString = inputStream.bufferedReader(Charset.defaultCharset()).use { it.readText() }
        val jsonObject = JSONObject(responseString)

        val firstName = jsonObject.getString("first_name")
        val lastName = jsonObject.getString("last_name")
        val email = jsonObject.getString("email")

        return ProfileResponse(
            firstName = firstName,
            lastName = lastName,
            email = email
        )
    }
}
