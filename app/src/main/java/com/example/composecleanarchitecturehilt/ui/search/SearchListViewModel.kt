package com.example.composecleanarchitecturehilt.ui.search

import android.app.Application
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SearchHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.domain.usecase.SearchHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private  val searchHistoryUseCase: SearchHistoryUseCase,
    application: Application
) : AndroidViewModel(application){

    val searchHistories = searchHistoryUseCase()
    private val vibrator = ContextCompat.getSystemService(application, Vibrator::class.java)

    fun insertSearchHistory(query: String) {
        val searchHistory = SearchHistory(query = query, timestamp = System.currentTimeMillis())
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryUseCase.insertSearchHistory(searchHistory)
        }
    }

    fun deleteSearchHistory(item: SearchHistory){
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryUseCase.deleteSearchHistory(item.query)
        }
    }

    fun vibrateOnLongClick() {
        vibrator?.let {
            if (it.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    it.vibrate(5)
                }
            }
        }
    }
}