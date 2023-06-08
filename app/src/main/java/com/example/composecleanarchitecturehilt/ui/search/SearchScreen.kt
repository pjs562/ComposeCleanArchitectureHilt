package com.example.composecleanarchitecturehilt.ui.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composecleanarchitecturehilt.ui.common.SegmentedButton
import com.example.composecleanarchitecturehilt.ui.image.ImageScreen
import com.example.composecleanarchitecturehilt.ui.image.ImageViewModel
import com.example.composecleanarchitecturehilt.ui.video.VideoScreen
import com.example.composecleanarchitecturehilt.ui.video.VideoViewModel
import com.example.composecleanarchitecturehilt.ui.web.WebScreen
import com.example.composecleanarchitecturehilt.ui.web.WebViewModel
import kotlinx.coroutines.launch

enum class SortOption(val text: String, val value: String) {
    Latest("최신순", "recency"),
    Accurate("정확도순", "accuracy")
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(navController: NavController, query: String, modifier: Modifier) {
    val titles = listOf("웹문서", "동영상", "사진")
    var selectedOption by rememberSaveable(key = "SearchScreenSelectedOption") { mutableStateOf(SortOption.Accurate) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        titles.size
    }
    val topAppBarState = rememberTopAppBarState()
    val scrollpBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(end = 12.dp),
                        onClick = { navController.navigate("searchList") },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.background,
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = query, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }, scrollBehavior = scrollpBehavior)

                val coroutineScope = rememberCoroutineScope()
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(text = title) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            }
                        )
                    }
                }
                TopAppBar(
                    title = {
                        SegmentedButton(selectedOption) {
                            selectedOption =
                                if (selectedOption == SortOption.Accurate) SortOption.Latest else SortOption.Accurate
                        }
                    }, scrollBehavior = scrollpBehavior
                )
            }
        },
        content = { paddingValues ->
            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(paddingValues),
                ) { index ->
                    when (index) {
                        0 -> {
                            val viewModel: WebViewModel = hiltViewModel()
                            WebScreen(
                                viewModel,
                                query,
                                selectedOption,
                                modifier.nestedScroll(scrollpBehavior.nestedScrollConnection)
                            )
                        }

                        1 -> {
                            val viewModel: VideoViewModel = hiltViewModel()
                            VideoScreen(
                                viewModel,
                                query,
                                selectedOption,
                                modifier.nestedScroll(scrollpBehavior.nestedScrollConnection)
                            )
                        }

                        2 -> {
                            val viewModel: ImageViewModel = hiltViewModel()
                            ImageScreen(
                                viewModel,
                                query,
                                selectedOption,
                                modifier.nestedScroll(scrollpBehavior.nestedScrollConnection)
                            )
                        }

                        else -> throw IllegalArgumentException("Invalid page: $index")
                    }
                }
            }
        }
    )
}