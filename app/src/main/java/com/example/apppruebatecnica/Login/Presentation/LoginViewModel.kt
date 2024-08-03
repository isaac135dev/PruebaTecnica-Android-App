package com.example.apppruebatecnica.Login.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppruebatecnica.Login.Domain.Use_Case.EmailFormatValidator
import com.example.apppruebatecnica.Login.Domain.Use_Case.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailFormatValidator: EmailFormatValidator,
    private val passwordValidator: PasswordValidator,
): ViewModel() {
}