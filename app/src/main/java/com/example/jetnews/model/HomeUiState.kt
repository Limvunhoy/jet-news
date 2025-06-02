package com.example.jetnews.model

data class HomeUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class Post(
    val id: Int,
    val author: String,
    val title: String,
    val date: String,
    val isFavorite: Boolean,
)