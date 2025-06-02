package com.example.jetnews.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnews.viewmodels.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.jetnews.data.model.UserEntity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.navigation.NavController
import com.example.jetnews.util.common.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {
    val users by viewModel.users.collectAsState(initial = emptyList())
    var newName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedUserToDelete by remember { mutableStateOf<UserEntity?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(title = "Users", navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            UserInputSection(name = newName, onNameChange = { newName = it }, onSubmit = {
                if (newName.isNotBlank()) {
                    viewModel.insertUser(newName)
                    newName = ""
                }
            })

            UserListSection(
                users = users,
                onDelete = {
                    selectedUserToDelete = it
                    showDialog = true
                }
            )
        }
    }

    if (showDialog && selectedUserToDelete != null) {
        DeleteConfirmationDialog(
            userName = selectedUserToDelete!!.name,
            onDismiss = { showDialog = false },
            onDeleteConfirm = {
                viewModel.deleteUser(selectedUserToDelete!!)
                showDialog = false
            }
        )
    }
}

@Composable
private fun UserItem(user: UserEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${user.name} (ID: ${user.id})",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(
                onClick = onDelete
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    userName: String,
    onDeleteConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Delete User")
        },
        text = {
            Text("Are you sure you want to delete \"$userName\"?")
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun UserInputSection(
    name: String,
    onNameChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    // Input Section
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Enter name") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
//            onDone = {
//                if (newName.isNotBlank()) {
//                    viewModel.insertUser(newName)
//                    newName = ""
//                }
//                focusManager.clearFocus()
//            }
            onDone = {
                onSubmit()
                focusManager.clearFocus()
            }
        )
    )

    Button(
//        onClick = {
//            if (newName.isNotBlank()) {
//                viewModel.insertUser(newName)
//                newName = ""
//                focusManager.clearFocus()
//            }
//        },
        onClick = {
            onSubmit()
            focusManager.clearFocus()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = name.isNotBlank()
    ) {
        Text("Add User")
    }
}

@Composable
fun UserListSection(users: List<UserEntity>, onDelete: (UserEntity) -> Unit) {
    // User List Section
    Text(
        text = "User List:",
        style = MaterialTheme.typography.titleMedium
    )

    if (users.isEmpty()) {
        Text(
            text = "No users yet",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                UserItem(user = user, onDelete = {
                    onDelete(user)
                })
            }
        }
    }
}