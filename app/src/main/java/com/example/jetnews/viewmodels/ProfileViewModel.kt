package com.example.jetnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.data.model.ProfileResponse
import com.example.jetnews.data.repository.Result
import com.example.jetnews.domain.repository.ProfileRepository
import com.example.jetnews.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

//    private val _profile = MutableStateFlow<ProfileResponse?>(null)
//    val profile: StateFlow<ProfileResponse?> = _profile
//
//    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)

    private  val _uiState = MutableStateFlow<UIState<ProfileResponse>>(UIState.Loading)
    val uiState: StateFlow<UIState<ProfileResponse>> = _uiState.asStateFlow()

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        val jsonBody = """
            {
                "first_name": "Vunhoy",
                "last_name": "Lim",
                "email": "example@gmail.com"
            }
        """.trimIndent()

        viewModelScope.launch {
            _uiState.value = UIState.Loading

            delay(1000)
            val result = profileRepository.getProfile(jsonBody)
            _uiState.value = when (result) {
                is Result.Error -> UIState.Error(result.exception.message.toString(), "Unknown code")
                is Result.Success<ProfileResponse> -> UIState.Success(result.data)
            }

//            _profile.value = when (result) {
//                is Result.Success -> result.data
//                is Result.Error -> null
//            }
        }
    }
}
