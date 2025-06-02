package com.example.jetnews.ui.profile

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnews.viewmodels.ProfileViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.jetnews.data.model.ProfileResponse
import com.example.jetnews.util.UIState

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel(), navHostController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (val state = uiState) {
            is UIState.Error -> {
//                val error = uiState as UIState.Error
                val message = state.message
                Text(
                    "Something went wrong $message",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Success<ProfileResponse> -> {
//                val profile = (uiState as UIState.Success).data
                val profile = state.data

                Column {
                    Text(
                        "Welcome to Profile Screen",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text("Username: ${profile.firstName}, ${profile.lastName}")
                    Text("Email: ${profile.email}")

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navHostController.navigate("user")
                        }
                    ) {
                        Text("Open User Screen")
                    }
                }
            }
        }
    }
}