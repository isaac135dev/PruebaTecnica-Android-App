package com.example.apppruebatecnica.SingUp.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppruebatecnica.Login.Domain.Use_Case.EmailFormatValidator
import com.example.apppruebatecnica.Login.Domain.Use_Case.PasswordValidator
import com.example.apppruebatecnica.SingUp.Domain.Use_Case.ConfirmPasswordValidator
import com.example.apppruebatecnica.SingUp.Domain.Use_Case.NumberPhoneValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val emailFormatValidator: EmailFormatValidator,
    private val numberPhoneValidator: NumberPhoneValidator,
    private val passwordValidator: PasswordValidator,
    private val confirmPasswordValidator: ConfirmPasswordValidator
) : ViewModel() {
}