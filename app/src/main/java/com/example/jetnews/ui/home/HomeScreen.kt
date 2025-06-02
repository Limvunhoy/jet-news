package com.example.jetnews.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.ui.theme.JetNewsTheme
import androidx.navigation.compose.composable
import com.example.jetnews.model.HomeUiState
import com.example.jetnews.model.Post
import com.example.jetnews.ui.detail.PostDetailScreen
import com.example.jetnews.viewmodels.HomeViewModel
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnews.util.common.AppTopBar

enum class JetNewsDestination() {
    Home,
    Detail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
//            TopAppBar(modifier = modifier, onClick = {
////                navController.navigate(JetNewsDestination.Detail.name)
//            })
            AppTopBar(title = "JetNews", navController)
        }, modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = JetNewsDestination.Home.name,
        ) {
            composable(JetNewsDestination.Home.name) {
                val state by viewModel.uiState.collectAsState()

                HomeView(
                    state = state,
                    onPostItemClick = { navController.navigate(JetNewsDestination.Detail.name) },
                    onFavoriteToggle = { viewModel.toggleFavorite(it) },
                    contentPadding = innerPadding,
                )
            }

            composable(route = JetNewsDestination.Detail.name) {
                PostDetailScreen(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    CenterAlignedTopAppBar(
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//        ),
        title = {
            Text("JetNews", modifier = modifier)
        },
        actions = {
            IconButton(onClick = onClick) {
                Icon(
                    Icons.Rounded.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun HomeView(
//    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: HomeUiState,
    onPostItemClick: (Int) -> Unit,
    onFavoriteToggle: (Int) -> Unit,
) {
    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp.dp

    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(contentPadding = contentPadding, modifier = Modifier.fillMaxSize()) {
        item {
            PostCardTopSection()
        }

        items(state.posts) { post ->
            PostListItem(
                post = post,
                onClick = {
                    onPostItemClick(post.id)
                },
                onFavoriteToggle = {
                    onFavoriteToggle(post.id)
                },
            )
            PostListDivider()
        }
    }
}

@Composable
fun PostListItem(
    post: Post,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Row(modifier = Modifier
        .clickable { onClick() }
        .padding(16.dp)) {

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(width = 50.dp, height = 35.dp)
        ) {}

        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp)) {
            Text(post.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text("${post.author} - ${post.date}", style = MaterialTheme.typography.bodySmall)
        }

        IconToggleButton(
            checked = post.isFavorite,
            onCheckedChange = { onFavoriteToggle() }
        ) {
            Icon(
                imageVector = if (post.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}

@Composable
fun PostCardTopSection(modifier: Modifier = Modifier) {
    Text(
        "Top stories for you",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(16.dp)
    )
    PostCard()
    PostListDivider()
}

@Composable
fun PostCard(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ), modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp)
    ) {
        // Card content here ...
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Redesigning the Android Studio Logo",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            "Android Studio Team", style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            "May 10 - 5 min read", style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 4.dp),
        )
    }
}

@Composable
fun PostListDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun GapHeight(modifier: Modifier = Modifier, height: Int = 16) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun GapWidth(modifier: Modifier = Modifier, width: Int = 16) {
    Spacer(modifier = Modifier.width(width.dp))
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    JetNewsTheme {
        HomeView(
            state = HomeUiState(),
            onPostItemClick = {},
            onFavoriteToggle = {}
        )
    }
}