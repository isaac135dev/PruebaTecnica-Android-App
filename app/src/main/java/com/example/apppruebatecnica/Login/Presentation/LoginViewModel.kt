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

    private val _isErrorValidate = MutableStateFlow(false)
    val isErrorValidate = _isErrorValidate.asStateFlow()

    private val _isErrorPassword = MutableStateFlow(false)
    val isErrorPasswordValidator = _isErrorPassword.asStateFlow()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

     fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        emailValidator(email)
         _isErrorPassword.value = !passwordValidator(password)
        _loginEnable.value = !_isErrorValidate.value && passwordValidator(password)
    }

    private fun emailValidator(emailValidator: String) {
        viewModelScope.launch {
            _isErrorValidate.value = !emailFormatValidator.invoke(emailValidator)
        }
    }
    private fun passwordValidator(password: String): Boolean{
        return passwordValidator.invoke(password)
    }
}