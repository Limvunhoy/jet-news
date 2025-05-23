package com.example.jetnews.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.ui.theme.JetNewsTheme
import androidx.navigation.compose.composable
import com.example.jetnews.ui.detail.PostDetailScreen

enum class JetNewsDestination() {
    Home,
    Detail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier, onClick = {
//                navController.navigate(JetNewsDestination.Detail.name)
            })
        }, modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = JetNewsDestination.Home.name,
        ) {
            composable(route = JetNewsDestination.Home.name) {
                HomeView(
                    contentPadding = innerPadding,
                    modifier = contentModifier
                        .fillMaxHeight(),
                    onPostItemClick = {
                        println("onTap post item at index: $it")
                    }
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
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
    onPostItemClick: (Int) -> Unit,
    ) {
    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp.dp

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state,
    ) {
        item {
            PostCardTopSection()
        }
        item {
            for (i in 1..3) {
                PostList(
                    modifier = Modifier
                        .clickable {
                            onPostItemClick(i)
                        }
                )
                PostListDivider()
            }
        }
    }
}

@Composable
fun PostList(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ), shape = RoundedCornerShape(8.dp), modifier = Modifier
                .padding(16.dp)
                .size(
                    width = 50.dp,
                    height = 35.dp,
                )
        ) { }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
        ) {
            Text("A Little Thing about Android Module Paths", style = MaterialTheme.typography.titleMedium,
                maxLines = 3, 
                overflow = TextOverflow.Ellipsis,
                )
            Text("Pietro Maggi - 1 min read", style = MaterialTheme.typography.bodyMedium)
        }
        IconToggleButton(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier
                .clearAndSetSemantics { }
                .padding(vertical = 2.dp, horizontal = 6.dp)) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder, contentDescription = null
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
            onPostItemClick = {}
        )
    }
}