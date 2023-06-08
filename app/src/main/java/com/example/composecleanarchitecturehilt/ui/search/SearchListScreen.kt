package com.example.composecleanarchitecturehilt.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composecleanarchitecturehilt.R
import com.example.composecleanarchitecturehilt.ui.navigation.HomeSections
import com.example.domain.model.SearchHistory
import kotlinx.coroutines.delay

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchListScreen(
    navController: NavController,
    onItemSelected: (HomeSections) -> Unit,
    onQueryChanged: (String) -> Unit,
    viewModel: SearchListViewModel = hiltViewModel(),
) {
    val searchHistories by viewModel.searchHistories.collectAsState(initial = emptyList())
    val (searchQuery, setSearchQuery) = rememberSaveable(key = "SearchListScreenSearchQuery") {
        mutableStateOf(
            ""
        )
    }
    var query by rememberSaveable(key = "SearchListQuery") { mutableStateOf("") }
    var active by rememberSaveable(key = "SearchListActive") { mutableStateOf(true) }
    var showDialog by rememberSaveable(key = "SearchListShowDialog") { mutableStateOf(false) }
    var selectedItem by remember {
        mutableStateOf<SearchHistory?>(
            null
        )
    }
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(modifier = Modifier.padding(4.dp)) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .focusRequester(focusRequester),
            query = searchQuery,
            onQueryChange = setSearchQuery,
            onSearch = {
                query = searchQuery
                viewModel.insertSearchHistory(query)
                active = false
                onItemSelected(HomeSections.SEARCH)
                onQueryChanged(query)
                navController.popBackStack()
            },
            active = active,
            onActiveChange = { isActive ->
                active = isActive
                if (!active)
                    navController.popBackStack()
            },
            placeholder = {
                Text(text = "검색")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.daum),
                    contentDescription = "daum",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty())
                    Icon(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .clickable {
                                if (searchQuery.isNotEmpty())
                                    setSearchQuery("")
                                else
                                    active = false
                            }
                            .padding(4.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp)
            ) {
                items(count = searchHistories.size) { index ->
                    Row(
                        modifier = Modifier
                            .combinedClickable(
                                onClick = {
                                    query = searchHistories[index].query
                                    viewModel.insertSearchHistory(searchHistories[index].query)
                                    active = false
                                    onItemSelected(HomeSections.SEARCH)
                                    onQueryChanged(searchHistories[index].query)
                                    navController.popBackStack()
                                },
                                onLongClick = {
                                    viewModel.vibrateOnLongClick()
                                    selectedItem = searchHistories[index]
                                    showDialog = true
                                }
                            )
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(end = 16.dp),
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = ""
                        )
                        Text(
                            text = searchHistories[index].query,
                            modifier = Modifier.widthIn(max = 200.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowOutward,
                            contentDescription = "",
                            modifier = Modifier
                                .graphicsLayer(scaleX = -1f)
                                .clip(
                                    CircleShape
                                )
                                .clickable {
                                    setSearchQuery(searchHistories[index].query)
                                }
                                .padding(14.dp)
                        )
                    }
                }
            }
        }
        selectedItem?.let {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("기록에서 이 검색어를 삭제할까요?") },
                    text = { Text("이전에 검색한 내용입니다. 기록에서 검색을(를) 삭제하면 검색어가 계정에서 완전히 삭제되며, 모든 기기에 반영됩니다.") },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Text("취소", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showDialog = false
                                viewModel.deleteSearchHistory(it)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background
                            ),
                        ) {
                            Text("삭제", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
        keyboardController?.show()
    }
}