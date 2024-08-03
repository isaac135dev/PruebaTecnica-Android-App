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
    
    private val _isErrorValidateEmail = MutableStateFlow(false)
    val isErrorValidateEmail = _isErrorValidateEmail.asStateFlow()

    private val _isErrorPassword = MutableStateFlow(false)
    val isErrorPassword = _isErrorPassword.asStateFlow()

    private val _isErrorPasswordConfirm = MutableStateFlow(false)
    val isErrorPasswordConfirm = _isErrorPasswordConfirm.asStateFlow()

    private val _isErrorValidatePhoneNumber = MutableStateFlow(false)
    val isErrorValidatePhoneNumber = _isErrorValidatePhoneNumber.asStateFlow()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordConfirm = MutableLiveData<String>()
    val passwordConfirm: LiveData<String> = _passwordConfirm

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    fun onSingUpChanged(
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        _email.value = email
        _phone.value = phone
        _password.value = password
        _passwordConfirm.value = confirmPassword
        emailValidator(email)
        phoneNumberValidate(phone)
        _isErrorPassword.value = !passwordValidator(password)
        _isErrorPasswordConfirm.value = !confirmPasswordValidator(password, confirmPassword)
        _loginEnable.value =
            !_isErrorValidateEmail.value
                    && passwordValidator(password)
                    && !_isErrorValidatePhoneNumber.value
                    && confirmPasswordValidator(password, confirmPassword)
    }

    fun phoneNumberValidate(numberPhone: String) {
        viewModelScope.launch {
            _isErrorValidatePhoneNumber.value = !numberPhoneValidator.invoke(numberPhone)
        }
    }

    fun emailValidator(emailValidator: String) {
        viewModelScope.launch {
            _isErrorValidateEmail.value = !emailFormatValidator.invoke(emailValidator)
        }
    }

    fun passwordValidator(password: String): Boolean {
        return passwordValidator.invoke(password)
    }

    fun confirmPasswordValidator(password: String, confirmPassword: String): Boolean {
        return confirmPasswordValidator.invoke(password, confirmPassword)
    }

}