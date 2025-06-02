package com.example.jetnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnews.model.HomeUiState
import com.example.jetnews.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private  val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(1000)
            _uiState.value = HomeUiState(
                posts = List(8) {
                    Post(
                        id = it,
                        title = "Post Title #$it",
                        author = "Author #$it",
                        date = "1 min read",
                        isFavorite = false
                    )
                },
                isLoading = false
            )
        }
    }

    fun toggleFavorite(id: Int) {
        val updatedPosts = _uiState.value.posts.map {
            if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
        }
        _uiState.value = _uiState.value.copy(posts = updatedPosts)
    }
}