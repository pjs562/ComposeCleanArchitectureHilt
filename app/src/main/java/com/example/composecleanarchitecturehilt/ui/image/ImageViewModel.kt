package com.example.composecleanarchitecturehilt.ui.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.composecleanarchitecturehilt.ui.search.SortOption
import com.example.domain.usecase.GetImageListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    getImageListUseCase: GetImageListUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery

    private val _sortOption = MutableStateFlow(SortOption.Accurate)
    private val sortOption = _sortOption

    fun changeValue(value: String, sortOption: SortOption) {
        _searchQuery.value = value
        _sortOption.value = sortOption
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val imageList = combine(_searchQuery, _sortOption) { query, sortOption ->
        Pair(query, sortOption)
    }.flatMapLatest { (query, sortOption) ->
        getImageListUseCase.invoke(query = query, sort = sortOption.value).cachedIn(viewModelScope)
    }
}