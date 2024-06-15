package com.chobo.presentation.viewModel.my

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// TODO: 파일 삭제 
@HiltViewModel
class MyBookEditViewModel @Inject constructor() : ViewModel() {
    private val _titleTextState = MutableStateFlow("")
    val titleTextState: StateFlow<String> = _titleTextState.asStateFlow()

    private val _writeTextState = MutableStateFlow("")
    val writeTextState: StateFlow<String> = _writeTextState.asStateFlow()

    private val _linkTextState = MutableStateFlow("")
    val linkTextState: StateFlow<String> = _linkTextState.asStateFlow()

    private val _titleTextStateIsEmpty = MutableStateFlow(false)
    val titleTextStateIsEmpty: StateFlow<Boolean> = _titleTextStateIsEmpty.asStateFlow()

    private val _writeTextStateIsEmpty = MutableStateFlow(false)
    val writeTextStateIsEmpty: StateFlow<Boolean> = _writeTextStateIsEmpty.asStateFlow()

    private val _linkTextStateIsEmpty = MutableStateFlow(false)
    val linkTextStateIsEmpty: StateFlow<Boolean> = _linkTextStateIsEmpty.asStateFlow()

    fun updateTitleTextState(input: String) {
        _titleTextStateIsEmpty.value = false
        _titleTextState.value = input
    }

    fun updateWriteTextState(input: String) {
        _writeTextStateIsEmpty.value = false
        _writeTextState.value = input
    }

    fun updateLinkTextState(input: String) {
        _linkTextStateIsEmpty.value = false
        _linkTextState.value = input
    }
}