package com.application.zazzyadmin.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MainViewModel(): ViewModel() {

    private val _imageUrlUiState = MutableStateFlow("")
    private val _fcmTokenUiState = MutableStateFlow("")
    private val _googleAuthUiState = MutableStateFlow("")

    val imageUrlUiState: StateFlow<String> = _imageUrlUiState.asStateFlow()
    val fcmTokenUiState: StateFlow<String> = _fcmTokenUiState.asStateFlow()
    val googleAuthUiState: StateFlow<String> = _googleAuthUiState.asStateFlow()

    fun setImageUrl(imageUrl: String) {
        _imageUrlUiState.value = imageUrl
    }

    fun setFcmToken(fcmToken: String){
        _fcmTokenUiState.value = fcmToken
    }

    fun setGoogleAuth(gmailAddress: String){
        _googleAuthUiState.value = gmailAddress
    }

}