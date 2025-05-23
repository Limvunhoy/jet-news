package com.example.jetnews.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetnews.ui.theme.JetNewsTheme

@Composable
fun PostDetailScreen(modifier: Modifier = Modifier) {
    Text("Post Detail Screen", style = MaterialTheme.typography.titleLarge, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
private fun PostDetailScreenPreview() {
    JetNewsTheme {
        PostDetailScreen()
    }
}